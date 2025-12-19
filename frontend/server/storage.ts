import { db } from "./db";
import { eq } from "drizzle-orm";
import {
  users, banks, accounts, transactions,
  type User, type InsertUser, type Bank, type Account, type InsertAccount, type Transaction, type InsertTransaction
} from "@shared/schema";

export interface IStorage {
  // Users
  getUser(id: number): Promise<User | undefined>;
  getUserByUsername(username: string): Promise<User | undefined>;
  createUser(user: InsertUser): Promise<User>;

  // Banks
  getBanks(): Promise<Bank[]>;
  seedBanks(): Promise<void>;

  // Accounts
  getAccounts(userId: number): Promise<(Account & { bank: Bank | null })[]>;
  createAccount(account: InsertAccount): Promise<Account>;
  linkAccount(id: number, isLinked: boolean): Promise<Account>;

  // Transactions
  getTransactions(accountId?: number): Promise<Transaction[]>;
  createTransaction(transaction: InsertTransaction): Promise<Transaction>;
}

export class DatabaseStorage implements IStorage {
  async getUser(id: number): Promise<User | undefined> {
    const [user] = await db.select().from(users).where(eq(users.id, id));
    return user;
  }

  async getUserByUsername(username: string): Promise<User | undefined> {
    const [user] = await db.select().from(users).where(eq(users.username, username));
    return user;
  }

  async createUser(insertUser: InsertUser): Promise<User> {
    const [user] = await db.insert(users).values(insertUser).returning();
    return user;
  }

  async getBanks(): Promise<Bank[]> {
    return await db.select().from(banks);
  }

  async seedBanks(): Promise<void> {
    const existing = await db.select().from(banks);
    if (existing.length === 0) {
      await db.insert(banks).values([
        { name: "Chase", icon: "Landmark" },
        { name: "Bank of America", icon: "Building2" },
        { name: "Citi", icon: "Globe" },
        { name: "Wells Fargo", icon: "Briefcase" },
        { name: "Goldman Sachs", icon: "TrendingUp" },
        { name: "HSBC", icon: "Landmark" },
        { name: "Barclays", icon: "Building" },
        { name: "Santander", icon: "CreditCard" },
        { name: "US Bank", icon: "Wallet" },
        { name: "PNC", icon: "DollarSign" },
      ]);
    }
  }

  async getAccounts(userId: number): Promise<(Account & { bank: Bank | null })[]> {
    // For now, since Drizzle's relational queries are powerful but I'm doing a manual join for safety/simplicity in this specific pattern
    // Actually, let's just use db.query if we had the query builder setup, but standard select with join is fine.
    // Drizzle ORM way:
    const result = await db.select({
      account: accounts,
      bank: banks
    })
    .from(accounts)
    .leftJoin(banks, eq(accounts.bankId, banks.id))
    .where(eq(accounts.userId, userId));

    return result.map(({ account, bank }) => ({ ...account, bank }));
  }

  async createAccount(insertAccount: InsertAccount): Promise<Account> {
    const [account] = await db.insert(accounts).values(insertAccount).returning();
    return account;
  }

  async linkAccount(id: number, isLinked: boolean): Promise<Account> {
    const [account] = await db.update(accounts)
      .set({ isLinked })
      .where(eq(accounts.id, id))
      .returning();
    return account;
  }

  async getTransactions(accountId?: number): Promise<Transaction[]> {
    if (accountId) {
      return await db.select().from(transactions).where(eq(transactions.accountId, accountId));
    }
    return await db.select().from(transactions);
  }

  async createTransaction(insertTransaction: InsertTransaction): Promise<Transaction> {
    const [transaction] = await db.insert(transactions).values(insertTransaction).returning();
    return transaction;
  }
}

export const storage = new DatabaseStorage();

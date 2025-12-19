import { pgTable, text, serial, integer, boolean, timestamp, numeric } from "drizzle-orm/pg-core";
import { relations } from "drizzle-orm";
import { createInsertSchema } from "drizzle-zod";
import { z } from "zod";

// === TABLE DEFINITIONS ===

export const users = pgTable("users", {
  id: serial("id").primaryKey(),
  username: text("username").notNull().unique(),
  password: text("password").notNull(), // Will mock auth
  fullName: text("full_name"),
  email: text("email"),
  mobile: text("mobile"),
  city: text("city"),
  country: text("country"),
  monthlyBudget: numeric("monthly_budget"),
  currency: text("currency").default("USD"),
  appPin: text("app_pin"),
  fingerprintEnabled: boolean("fingerprint_enabled").default(false),
  isProfileComplete: boolean("is_profile_complete").default(false),
  createdAt: timestamp("created_at").defaultNow(),
});

export const banks = pgTable("banks", {
  id: serial("id").primaryKey(),
  name: text("name").notNull(),
  icon: text("icon"), // Lucide icon name or similar
});

export const accounts = pgTable("accounts", {
  id: serial("id").primaryKey(),
  userId: integer("user_id").references(() => users.id),
  bankId: integer("bank_id").references(() => banks.id),
  accountNumber: text("account_number").notNull(), // Masked usually
  type: text("type").notNull(), // Savings, Current, Loan
  balance: numeric("balance").notNull().default('0'),
  isLinked: boolean("is_linked").default(false),
  // Loan specific fields
  loanAmount: numeric("loan_amount"),
  loanPaid: numeric("loan_paid"),
  createdAt: timestamp("created_at").defaultNow(),
});

export const transactions = pgTable("transactions", {
  id: serial("id").primaryKey(),
  accountId: integer("account_id").references(() => accounts.id),
  amount: numeric("amount").notNull(),
  type: text("type").notNull(), // credit, debit
  category: text("category"), // Food, Shopping, Rent, etc.
  description: text("description"),
  date: timestamp("date").defaultNow(),
});

// === RELATIONS ===

export const usersRelations = relations(users, ({ many }) => ({
  accounts: many(accounts),
}));

export const banksRelations = relations(banks, ({ many }) => ({
  accounts: many(accounts),
}));

export const accountsRelations = relations(accounts, ({ one, many }) => ({
  user: one(users, {
    fields: [accounts.userId],
    references: [users.id],
  }),
  bank: one(banks, {
    fields: [accounts.bankId],
    references: [banks.id],
  }),
  transactions: many(transactions),
}));

export const transactionsRelations = relations(transactions, ({ one }) => ({
  account: one(accounts, {
    fields: [transactions.accountId],
    references: [accounts.id],
  }),
}));

// === BASE SCHEMAS ===

export const insertUserSchema = createInsertSchema(users).omit({ id: true, createdAt: true });
export const insertBankSchema = createInsertSchema(banks).omit({ id: true });
export const insertAccountSchema = createInsertSchema(accounts).omit({ id: true, createdAt: true });
export const insertTransactionSchema = createInsertSchema(transactions).omit({ id: true, date: true });

// === EXPLICIT API CONTRACT TYPES ===

export type User = typeof users.$inferSelect;
export type InsertUser = z.infer<typeof insertUserSchema>;
export type Bank = typeof banks.$inferSelect;
export type Account = typeof accounts.$inferSelect;
export type Transaction = typeof transactions.$inferSelect;

export type CreateUserRequest = InsertUser;
export type UpdateUserRequest = Partial<InsertUser>;
export type CreateAccountRequest = InsertAccount;
export type CreateTransactionRequest = InsertTransaction;

// Response types
export type UserResponse = User;
export type BankResponse = Bank;
export type AccountResponse = Account & { bank?: Bank };
export type TransactionResponse = Transaction;

// For the multi-step signup
export type SignupStep1Request = Pick<InsertUser, "fullName" | "email" | "mobile" | "city" | "country">;
export type SignupStep2Request = { linkedAccountIds: number[] }; // IDs of demo accounts to link
export type SignupStep3Request = Pick<InsertUser, "monthlyBudget" | "currency">; // And categories preference (could be stored in user or implied)
export type SignupStep4Request = Pick<InsertUser, "username" | "password" | "appPin" | "fingerprintEnabled">;

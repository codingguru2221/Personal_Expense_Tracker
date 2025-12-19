import { db } from './db';
import { users, banks, accounts, transactions } from '@shared/schema';
import { sql } from 'drizzle-orm';

async function initDatabase() {
  try {
    // Create tables
    await db.run(sql`
      CREATE TABLE IF NOT EXISTS users (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        username TEXT NOT NULL UNIQUE,
        password TEXT NOT NULL,
        full_name TEXT,
        email TEXT,
        mobile TEXT,
        city TEXT,
        country TEXT,
        monthly_budget NUMERIC,
        currency TEXT DEFAULT 'USD',
        app_pin TEXT,
        fingerprint_enabled BOOLEAN DEFAULT FALSE,
        is_profile_complete BOOLEAN DEFAULT FALSE,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
      )
    `);

    await db.run(sql`
      CREATE TABLE IF NOT EXISTS banks (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        name TEXT NOT NULL,
        icon TEXT
      )
    `);

    await db.run(sql`
      CREATE TABLE IF NOT EXISTS accounts (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        user_id INTEGER REFERENCES users(id),
        bank_id INTEGER REFERENCES banks(id),
        account_number TEXT NOT NULL,
        type TEXT NOT NULL,
        balance NUMERIC NOT NULL DEFAULT 0,
        is_linked BOOLEAN DEFAULT FALSE,
        loan_amount NUMERIC,
        loan_paid NUMERIC,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
      )
    `);

    await db.run(sql`
      CREATE TABLE IF NOT EXISTS transactions (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        account_id INTEGER REFERENCES accounts(id),
        amount NUMERIC NOT NULL,
        type TEXT NOT NULL,
        category TEXT,
        description TEXT,
        date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
      )
    `);

    // Insert some sample data
    await db.insert(banks).values([
      { name: 'Chase', icon: 'bank' },
      { name: 'Wells Fargo', icon: 'bank' },
      { name: 'Bank of America', icon: 'bank' }
    ]);

    console.log('Database initialized successfully');
  } catch (error) {
    console.error('Error initializing database:', error);
  }
}

initDatabase();
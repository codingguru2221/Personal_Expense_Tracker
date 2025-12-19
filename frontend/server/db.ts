import { drizzle } from 'drizzle-orm/better-sqlite3';
import Database from 'better-sqlite3';
import * as schema from "@shared/schema";

// Use SQLite for both development and production (for simplicity)
const sqlite = new Database('database.db');
export const db = drizzle(sqlite, { schema });

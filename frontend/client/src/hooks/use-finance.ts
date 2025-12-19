import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { api, buildUrl, type InsertUser, type InsertAccount } from "@shared/routes";
import { useToast } from "@/hooks/use-toast";

// === USERS ===

export function useUser(id?: number) {
  return useQuery({
    queryKey: [api.users.get.path, id],
    queryFn: async () => {
      if (!id) return null;
      const url = buildUrl(api.users.get.path, { id });
      const res = await fetch(url);
      if (res.status === 404) return null;
      if (!res.ok) throw new Error("Failed to fetch user");
      return api.users.get.responses[200].parse(await res.json());
    },
    enabled: !!id,
  });
}

export function useCreateUser() {
  const { toast } = useToast();
  return useMutation({
    mutationFn: async (data: InsertUser) => {
      const res = await fetch(api.users.create.path, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data),
      });
      if (!res.ok) {
        const error = await res.json();
        throw new Error(error.message || "Failed to create user");
      }
      return api.users.create.responses[201].parse(await res.json());
    },
    onError: (error) => {
      toast({
        title: "Registration Failed",
        description: error.message,
        variant: "destructive",
      });
    },
  });
}

// === BANKS ===

export function useBanks() {
  return useQuery({
    queryKey: [api.banks.list.path],
    queryFn: async () => {
      const res = await fetch(api.banks.list.path);
      if (!res.ok) throw new Error("Failed to fetch banks");
      return api.banks.list.responses[200].parse(await res.json());
    },
  });
}

// === ACCOUNTS ===

export function useAccounts(userId?: number) {
  return useQuery({
    queryKey: [api.accounts.list.path, userId],
    queryFn: async () => {
      if (!userId) return [];
      const url = `${api.accounts.list.path}?userId=${userId}`;
      const res = await fetch(url);
      if (!res.ok) throw new Error("Failed to fetch accounts");
      return api.accounts.list.responses[200].parse(await res.json());
    },
    enabled: !!userId,
  });
}

export function useCreateAccount() {
  const queryClient = useQueryClient();
  const { toast } = useToast();
  return useMutation({
    mutationFn: async (data: InsertAccount) => {
      const res = await fetch(api.accounts.create.path, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data),
      });
      if (!res.ok) throw new Error("Failed to create account");
      return api.accounts.create.responses[201].parse(await res.json());
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [api.accounts.list.path] });
      toast({ title: "Account Created", description: "Successfully linked new account." });
    },
  });
}

export function useLinkAccount() {
  const queryClient = useQueryClient();
  const { toast } = useToast();
  return useMutation({
    mutationFn: async ({ id, isLinked }: { id: number; isLinked: boolean }) => {
      const url = buildUrl(api.accounts.link.path, { id });
      const res = await fetch(url, {
        method: "PATCH",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ isLinked }),
      });
      if (!res.ok) throw new Error("Failed to link account");
      return api.accounts.link.responses[200].parse(await res.json());
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [api.accounts.list.path] });
      toast({ title: "Success", description: "Account status updated." });
    },
  });
}

// === TRANSACTIONS ===

export function useTransactions(accountId?: number) {
  return useQuery({
    queryKey: [api.transactions.list.path, accountId],
    queryFn: async () => {
      let url = api.transactions.list.path;
      if (accountId) {
        url += `?accountId=${accountId}`;
      }
      const res = await fetch(url);
      if (!res.ok) throw new Error("Failed to fetch transactions");
      return api.transactions.list.responses[200].parse(await res.json());
    },
  });
}

import { Navbar, MobileNav } from "@/components/layout/Navbar";
import { ButtonCustom } from "@/components/ui/button-custom";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Progress } from "@/components/ui/progress";
import { Wallet, Calendar, AlertCircle } from "lucide-react";

export default function Loans() {
  const mockLoans = [
    { id: 1, type: "Home Loan", amount: 250000, paid: 85000, interest: 4.5, nextDue: "Oct 15, 2025" },
    { id: 2, type: "Car Loan", amount: 35000, paid: 12000, interest: 6.2, nextDue: "Oct 20, 2025" },
  ];

  return (
    <div className="min-h-screen bg-background text-foreground flex">
      <Navbar />
      
      <main className="flex-1 lg:ml-64 p-4 lg:p-8 pb-24 lg:pb-8 max-w-[1200px] mx-auto w-full">
        <header className="mb-8">
          <h1 className="text-3xl font-display font-bold">Loans & Liabilities</h1>
          <p className="text-muted-foreground">Track your repayment progress and upcoming EMIs.</p>
        </header>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-8">
          <Card className="bg-gradient-to-br from-indigo-900 to-slate-900 text-white border-0 shadow-xl">
            <CardHeader>
              <CardTitle className="flex justify-between items-center text-lg font-medium text-white/80">
                <span>Total Outstanding</span>
                <Wallet className="w-5 h-5" />
              </CardTitle>
            </CardHeader>
            <CardContent>
              <div className="text-4xl font-display font-bold mb-2">$188,000</div>
              <p className="text-sm text-indigo-200">Total initial principal: $285,000</p>
            </CardContent>
          </Card>

          <Card className="bg-white dark:bg-card shadow-lg border-l-4 border-l-amber-500">
            <CardHeader>
              <CardTitle className="flex justify-between items-center text-lg font-medium">
                <span>Next EMI Due</span>
                <AlertCircle className="w-5 h-5 text-amber-500" />
              </CardTitle>
            </CardHeader>
            <CardContent>
              <div className="text-4xl font-display font-bold mb-2">$2,450</div>
              <p className="text-sm text-muted-foreground flex items-center gap-2">
                <Calendar className="w-4 h-4" /> Due on Oct 15, 2025
              </p>
              <ButtonCustom variant="outline" size="sm" className="mt-4 w-full">Pay Now</ButtonCustom>
            </CardContent>
          </Card>
        </div>

        <div className="space-y-6">
          <h3 className="text-xl font-bold font-display">Active Loans</h3>
          {mockLoans.map((loan) => {
            const percentage = Math.round((loan.paid / loan.amount) * 100);
            return (
              <div key={loan.id} className="bg-card rounded-2xl p-6 border border-border shadow-sm">
                <div className="flex flex-col md:flex-row justify-between md:items-center mb-6 gap-4">
                  <div>
                    <h4 className="text-lg font-bold">{loan.type}</h4>
                    <p className="text-sm text-muted-foreground">Interest Rate: {loan.interest}%</p>
                  </div>
                  <div className="text-right">
                    <p className="text-sm text-muted-foreground">Remaining</p>
                    <p className="text-2xl font-bold font-display">${(loan.amount - loan.paid).toLocaleString()}</p>
                  </div>
                </div>

                <div className="space-y-2">
                  <div className="flex justify-between text-sm font-medium">
                    <span>Paid: ${loan.paid.toLocaleString()}</span>
                    <span>{percentage}%</span>
                  </div>
                  <Progress value={percentage} className="h-3" />
                  <div className="text-right text-xs text-muted-foreground">Total: ${loan.amount.toLocaleString()}</div>
                </div>
              </div>
            );
          })}
        </div>
      </main>

      <MobileNav />
    </div>
  );
}

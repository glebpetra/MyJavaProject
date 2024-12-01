#include <iostream>
#include <vector>
#include <stdexcept>

std::vector<long long> fibonacci(size_t n) {
    if (n == 0)
        return {};
    if (n < 0)
        throw std::invalid_argument("n must be non-negative");

    std::vector<long long> fib_numbers;
    fib_numbers.reserve(n);

    fib_numbers.push_back(0);
    if (n == 1) 
        return fib_numbers;
    fib_numbers.push_back(1);
    if (n == 2) 
        return fib_numbers;

    long long a = 0;
    long long b = 1;
    long long next_fib;

    for (size_t i = 2; i < n; ++i) {
        next_fib = a + b;
        if (next_fib < a || next_fib < b) {
            throw std::overflow_error("Fibonacci number exceeds maximum value");
        }
        fib_numbers.push_back(next_fib);
        a = b;
        b = next_fib;
    }

    return fib_numbers;
}

int main() {
    int n = 0;
    std::cin >> n;
    std::vector <long long> fib = fibonacci(n);
    for (int i = 0; i < fib.size(); i++) {
        std::cout << fib[i] << " ";
    }
    return 0;
}
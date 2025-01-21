#include <iostream>
#include <vector>
#include <stdexcept>

long long addition(long long first_sum, long long second_sum) {
    long long sum = first_sum + second_sum;
    if (sum < first_sum || sum < second_sum) {
        throw std::overflow_error("Fibonacci number exceeds maximum value");
    }
    return sum;
}

std::vector<long long> fibonacci(long long size) {
    if (size == 0)
        return {};
    if (size < 0)
        throw std::invalid_argument("n must be non-negative");

    std::vector<long long> fib_numbers;
    fib_numbers.reserve(size);

    fib_numbers.push_back(0);
    if (size == 1) 
        return fib_numbers;
    fib_numbers.push_back(1);
    if (size == 2)
        return fib_numbers;

    long long first_sum = 0;
    long long sec_sum = 1;
    long long next_fib = 0;

    for (size_t i = 2; i < size; ++i) {
        next_fib = addition(first_sum, sec_sum);
        /*if (next_fib < size || next_fib < size) {
            throw std::overflow_error("Fibonacci number exceeds maximum value");
        }*/
        first_sum = sec_sum;
        sec_sum = next_fib;
        fib_numbers.push_back(next_fib);
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
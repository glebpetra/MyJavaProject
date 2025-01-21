#include "pch.h"
#include "CppUnitTest.h"
#include "../Fibonacchi/Fibonacchi.cpp"

using namespace Microsoft::VisualStudio::CppUnitTestFramework;

namespace UnitTestFibonacci
{
	TEST_CLASS(UnitTestFibonacci)
	{
	public:

		TEST_METHOD(Addition_ByCorrectInput_Sum) {
			long long expected = 96;

			long long result = addition(90, 6);

			Assert::IsTrue(result == expected);
		}

		TEST_METHOD(Addition_ByInputTooBigNumb_OverflowError) {
			auto result = []() {addition(LLONG_MAX, 1);};

			Assert::ExpectException<std::overflow_error>(result);
		}

		TEST_METHOD(Fibonacci_ByInputZero_Empty) {
			std::vector<long long> result = fibonacci(0);

			Assert::IsTrue(result.empty());
		}

		TEST_METHOD(Fibonacci_ByInputOne_Zero) {
			std::vector<long long> expected{ 0 };

			std::vector<long long> result = fibonacci(1);

			Assert::IsTrue(result == expected);
		}

		TEST_METHOD(Fibonacci_ByInputTwo_ZeroAndOne) {
			std::vector<long long> expected{ 0, 1 };

			std::vector<long long> result = fibonacci(2);

			Assert::IsTrue(result == expected);
		}

		TEST_METHOD(Fibonacci_ByInputTenNumbers_UsualSequence) {
			std::vector<long long> expected{ 0, 1, 1, 2, 3, 5, 8, 13, 21, 34 };

			std::vector<long long> result = fibonacci(10);

			Assert::IsTrue(result == expected);
		}

		TEST_METHOD(Fibonacci_ByInputNegative_InvalidArgument) {
			auto result = [] () {fibonacci(-10);};

			Assert::ExpectException<std::invalid_argument>(result);
		}

		TEST_METHOD(Fibonacci_ByInputTooBigNumb_OverflowError) {
			auto result = [] () {fibonacci(94);};

			Assert::ExpectException<std::overflow_error>(result);
		}
	};
}

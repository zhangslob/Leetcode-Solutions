# Pow(x, n)

[原题链接](https://leetcode-cn.com/problems/powx-n/)

## 题目

实现 pow(x, n) ，即计算 x 的 n 次幂函数。

示例 1:
```text
输入: 2.00000, 10
输出: 1024.00000
```

示例 2:
```text
输入: 2.10000, 3
输出: 9.26100
```

示例 3:
```text
输入: 2.00000, -2
输出: 0.25000
解释: 2-2 = 1/22 = 1/4 = 0.25
```

说明:
1. -100.0 < x < 100.0
2. n 是 32 位有符号整数，其数值范围是 [$−2^31$, $2^31$ − 1] 。

## 题解

以下内容来自官方题解

### 快速幂 + 递归

「快速幂算法」的本质是分治算法。举个例子，如果我们要计算 $x^{64}x$  ，我们可以按照：

x $\to$$ x^2$ $\to$ $x^4$ $\to$ x^8$ \to$ $x^{16}$ $\to$$ x^{32}$$ \to$$ x^{64}$的顺序，从 $x$ 开始，每次直接把上一次的结果进行平方，计算 6 次就可以得到 $x^{64}$的值，而不需要对 x 乘 63 次 x。

再举一个例子，如果我们要计算 $x^{77}$ ，我们可以按照：

$x$$ \to$$ x^2$$ \to$$ x^4$$ \to$$ x^9$$ \to$$ x^{19}$$ \to$$ x^{38}$$ \to$$ x^{77}$的顺序，在 $x$$ \to$$ x^2$，$x^2$$ \to$$ x^4$ ，$x^{19}$$ \to$$ x^{38}$这 些步骤中，我们直接把上一次的结果进行平方，而在 $x^4$$ \to$$ x^9$ ，$x^9$$ \to$$ x^{19}$，$x^{38}$$ \to$$ x^{77}$
  这些步骤中，我们把上一次的结果进行平方后，还要额外乘一个 x。

直接从左到右进行推导看上去很困难，因为在每一步中，我们不知道在将上一次的结果平方之后，还需不需要额外乘 x。但如果我们从右往左看，分治的思想就十分明显了：

- 当我们要计算 $x^n$ 时，我们可以先递归地计算出$ y = x^{\lfloor n/2 \rfloor}$，其中$ \lfloor a \rfloor$ 表示对 $a$ 进行下取整；
- 根据递归计算的结果，如果 $n$ 为偶数，那么$ x^n = y^2$x  ；如果 $n$ 为奇数，那么 $x^n = y^2 * x$；
- 递归的边界为$n=0$，任意数的 0 次方均为 1。

由于每次递归都会使得指数减少一半，因此递归的层数为 $O(\log n)$，算法可以在很快的时间内得到结果。

## 代码

### java
```java
class Solution {
    public double myPow(double x, int n) {
        long N = n;
        if (n >= 0) {
            return quickMul(x, N);
        } else {
            return 1.0 / quickMul(x, -N);
        }
    }

    public double quickMul(double x, long N) {
        if (N == 0) {
            return 1.0;
        }
        double y = quickMul(x, N / 2);
        if (N % 2 == 0) {
            return y * y;
        } else {
            return y * y * x;
        }
    }
}
```

- 执行用时 : 1 ms , 在所有 Java 提交中击败了 94.50% 的用户 
- 内存消耗 : 37.3 MB , 在所有 Java 提交中击败了 5.88% 的用户

### Go

```go
func myPow(x float64, n int) float64 {
	if n >= 0 {
		return quickMul(x, n)
	}
	return 1.0 / quickMul(x, -n)
}

func quickMul(x float64, n int) float64 {
	if n == 0 {
		return 1
	}
	y := quickMul(x, n/2)
	if n%2 == 0 {
		return y * y
	}
	return y * y * x
}
```

- 执行用时 : 0 ms , 在所有 Go 提交中击败了 100.00% 的用户 
- 内存消耗 : 2 MB , 在所有 Go 提交中击败了 100.00% 的用户

### Python
```python
class Solution:
    def myPow(self, x: float, n: int) -> float:
        def quickMul(N):
            if N == 0:
                return 1
            y = quickMul(N // 2)
            if N % 2 == 0:
                return y * y
            else:
                return y * y * x

        return quickMul(n) if n >= 0 else 1.0 / quickMul(-n)
```

- 执行用时 : 44 ms , 在所有 Python3 提交中击败了 46.34% 的用户 
- 内存消耗 : 13.9 MB , 在所有 Python3 提交中击败了 8.33% 的用户


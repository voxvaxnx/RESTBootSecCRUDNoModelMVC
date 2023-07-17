package ru.kata.spring.boot_security.demo;

import org.junit.jupiter.api.Test;
import ru.kata.spring.boot_security.demo.services.Solution;

import java.util.Arrays;

public class SolutionTest {

    @Test
    void SolutionIntTest(){
        Solution solution = new Solution();
        System.out.println(Arrays.toString(solution.twoSum(new int[]{3,3}, 6)));

    }
}

package com.adavydenko.tictactoe.battleservice.services.impl;

import com.adavydenko.tictactoe.battleservice.entities.Step;
import com.adavydenko.tictactoe.battleservice.repositories.BattleRepository;
import com.adavydenko.tictactoe.battleservice.repositories.GridRepository;
import com.adavydenko.tictactoe.battleservice.repositories.StepRepository;
import com.adavydenko.tictactoe.userservice.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class BattleServiceImplTest {
    @Mock
    UserService userService;
    @Mock
    BattleRepository battleRepository;
    @Mock
    GridRepository gridRepository;
    @Mock
    StepRepository stepRepository;

    @InjectMocks
    BattleServiceImpl battleService;

    List<Step> steps;

    @BeforeEach
    void beforeEach() {
        /** Grid:
         *    1 2 3
         *    - - -
         * 1| 1 1 0
         * 2| 1 1 0
         * 3| 1 0 0
         * */
        Step step11 = new Step();
        step11.setX(1);
        step11.setY(1);
        Step step12 = new Step();
        step12.setX(1);
        step12.setY(2);
        Step step13 = new Step();
        step13.setX(1);
        step13.setY(3);
        Step step21 = new Step();
        step21.setX(2);
        step21.setY(1);
        Step step22 = new Step();
        step22.setX(2);
        step22.setY(2);

        steps = Arrays.asList(step13, step22, step12, step21, step11);
    }

    @Test
    void sortSteps_test() {
        battleService.sortSteps(steps);

        Assertions.assertEquals(steps.get(0).getX(), 1);
        Assertions.assertEquals(steps.get(0).getY(), 1);

        Assertions.assertEquals(steps.get(1).getX(), 1);
        Assertions.assertEquals(steps.get(1).getY(), 2);

        Assertions.assertEquals(steps.get(2).getX(), 1);
        Assertions.assertEquals(steps.get(2).getY(), 3);

        Assertions.assertEquals(steps.get(3).getX(), 2);
        Assertions.assertEquals(steps.get(3).getY(), 1);

        Assertions.assertEquals(steps.get(4).getX(), 2);
        Assertions.assertEquals(steps.get(4).getY(), 2);
    }

    @Test
    void calculateNOfStepsInRow_test() {
        Step currentStep = steps.stream().filter(step -> step.getX() == 1 && step.getY() == 1).findFirst().orElse(null);

        Assertions.assertEquals(battleService.calculateNOfStepsInRow(currentStep, steps, 1, 0), 2);
        Assertions.assertEquals(battleService.calculateNOfStepsInRow(currentStep, steps, 0, 1), 3);
        Assertions.assertEquals(battleService.calculateNOfStepsInRow(currentStep, steps, 1, 1), 2);
        Assertions.assertEquals(battleService.calculateNOfStepsInRow(currentStep, steps, 1, -1), 1);
    }
}
package com.survivalcoding.assignments_01_instance.exam01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.survivalcoding.assignments_01_instance.exam01.Cleric.MAX_HP;
import static com.survivalcoding.assignments_01_instance.exam01.Cleric.MAX_MP;
import static org.junit.jupiter.api.Assertions.*;

class ClericTest {


    @Test
    @DisplayName("클래릭 생성시 설정한 이름으로 지정되어야 하고, MAX_HP / MAX_MP 로 설정되어야 한다")
    void constructorTest1() {
        //given
        String name = "z1존법사";
        //when
        Cleric cleric = new Cleric(name);

        //then
        assertEquals(name, cleric.getName());
        assertEquals(Cleric.MAX_HP, cleric.getHp());
        assertEquals(MAX_MP, cleric.getMp());
    }

    @Test
    @DisplayName("클래릭 생성시 이름이 공백이거나 NULL 인경우 예외가 발생한다.")
    void constructorTest1_1() {
        //give//when//then
        assertThrows(RuntimeException.class, () -> new Cleric(null));
        assertThrows(RuntimeException.class, () -> new Cleric("    "));
    }

    @Test
    @DisplayName("클래릭 생성시 설정한 이름과 HP으로 지정되어야 하고 MP는 MAX_MP 로 설정되어야 한다")
    void constructorTest2() {
        //given
        String name = "z1존법사";
        int hp = 10;
        //when
        Cleric cleric = new Cleric(name, hp);

        //then
        assertEquals(name, cleric.getName());
        assertEquals(hp, cleric.getHp());
        assertEquals(MAX_MP, cleric.getMp());
    }

    @Test
    @DisplayName("클래릭 생성시 HP는 음수일 수 없고 MAX_HP 보다 클경우 예외가 발생한다.")
    void constructorTest2_1() {
        //give//when//then
        assertThrows(RuntimeException.class, () -> new Cleric("test", -1));
        assertThrows(RuntimeException.class, () -> new Cleric("test", MAX_HP + 1));
    }

    @Test
    @DisplayName("클래릭 생성시 설정한 이름과 HP, MP로 설정되어야 한다")
    void constructorTest3() {
        //given
        String name = "z1존법사";
        int hp = 10;
        int mp = 5;
        //when
        Cleric cleric = new Cleric(name, hp, mp);

        //then
        assertEquals(name, cleric.getName());
        assertEquals(hp, cleric.getHp());
        assertEquals(mp, cleric.getMp());
    }

    @Test
    @DisplayName("클래릭 생성시 HP와 MP는 음수일 수 없고 MAX_HP 보다 클경우 예외가 발생한다.")
    void constructorTest3_1() {
        //give//when//then
        assertThrows(RuntimeException.class, () -> new Cleric("test", -1, -1));
        assertThrows(RuntimeException.class, () -> new Cleric("test", 0, -1));
        assertThrows(RuntimeException.class, () -> new Cleric("test", -1, 0));
        assertThrows(RuntimeException.class, () -> new Cleric("test", MAX_HP + 1, MAX_MP + 1));
        assertThrows(RuntimeException.class, () -> new Cleric("test", MAX_HP + 1, 0));
        assertThrows(RuntimeException.class, () -> new Cleric("test", 0, MAX_MP + 1));
    }

    @Test
    @DisplayName("selfAid 사용시 MP가 5 감소해야하고 HP가 최대로 회복되어야 한다.")
    void selfAid() {
        //given
        int hp = 10;
        int mp = 5;

        Cleric testCleric = new Cleric("test", hp, mp);

        //when
        testCleric.selfAid();
        //then
        assertEquals(Cleric.MAX_HP, testCleric.getHp());
        assertEquals(0, testCleric.getMp());
    }

    @Test
    @DisplayName("selfAid 사용시 MP가 5 미만인 경우 HP가 회복되지 않으며 MP가 소모되지 않는다.")
    void selfAidException() {
        //given
        int hp = 10;
        int mp = 2;

        Cleric testCleric = new Cleric("test", hp, mp);

        //when
        testCleric.selfAid();
        //then
        assertEquals(hp, testCleric.getHp());
        assertEquals(mp, testCleric.getMp());
    }

    @Test
    @DisplayName("getRandomNumber 사용시 min - max 범위의 수가 나와야 한다.")
    void getRandomNumber() {
        //given
        int min = 0;
        int max = 2;
        int repeat = 100;
        int[] intArr = new int[100];
        //when
        for (int i = 0; i < repeat; i++) {
            int randomNumber = Cleric.getRandomNumber(min, max);
            intArr[i] = randomNumber;
        }
        //then
        for (int number : intArr) {
            assertTrue(number >= min && number <= max);
        }
    }


    @Test
    @DisplayName("pray 사용시 n초 기도시 MP 회복량은 n + min ~ n + max 값이여야한다.")
    void pray() {
        //given
        int hp = 10;
        int mp = 0;
        int min = 0;
        int max = 2;
        int praySec = 3;

        Cleric testCleric = new Cleric("test", hp, mp);

        //when
        int amount = testCleric.pray(praySec);

        //then
        Assertions.assertTrue(amount >= praySec + min && amount <= praySec + max);
        Assertions.assertTrue(testCleric.getMp() >= mp);
    }


    @Test
    @DisplayName("pray 사용시 mp 최대치 도달 시 회복된 만큼만 반환되어야 한다.")
    void prayFullCharging() {
        //given
        int hp = 10;
        int mp = 9;
        int praySec = 3;

        Cleric testCleric = new Cleric("test", hp, mp);

        //when
        int result = testCleric.pray(praySec);
        //then
        assertEquals(MAX_MP, testCleric.getMp());
        assertEquals(MAX_MP - mp, result);
    }

    @Test
    @DisplayName("pray 사용시 mp가 이미 최대치 인경우 회복량이 0 이다.")
    void prayException() {
        //given
        int hp = 10;
        int praySec = 3;

        Cleric testCleric = new Cleric("test", hp, MAX_MP);

        //when
        int maxResult = testCleric.pray(praySec);
        // then
        assertEquals(0, maxResult);
    }

    @Test
    @DisplayName("pray 사용시 sec이 0이나 음수일 경우 회복량이 0 이다.")
    void prayExceptionForSec() {
        //given
        int hp = 10;
        int mp = 9;

        Cleric testCleric = new Cleric("test", hp, mp);

        //when
        int negativeResult = testCleric.pray(-3);
        int zeroResult = testCleric.pray(0);
        //then
        assertEquals(0, negativeResult);
        assertEquals(0, zeroResult);

    }

    @Test
    @DisplayName("회복 스킬을 받을 경우 amount 만큼 회복된다.")
    void beHealed(){
        //given
        int hp = 10;
        int amount = 20;
        Cleric cleric = new Cleric("Cleric", hp);

        //when
        cleric.beHealed(amount);

        //then
        assertEquals(hp + amount, cleric.getHp());

    }

    @Test
    @DisplayName("회복 스킬을 받을 경우 MAX_HP 이상인 경우 Max까지만 회복된다.")
    void beHealed_2(){
        //given
        int amount = 20;
        Cleric cleric = new Cleric("Cleric", MAX_HP);

        //when
        cleric.beHealed(amount);

        //then
        assertEquals(MAX_HP, cleric.getHp());

    }


}
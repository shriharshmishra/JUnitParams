package junitparams.usage.person_example;

import static junitparams.JUnitParamsRunner.*;
import static org.assertj.core.api.Assertions.assertThat;

import junitparams.testnaming.TestCaseName;
import org.junit.*;
import org.junit.runner.*;

import junitparams.*;

@RunWith(JUnitParamsRunner.class)
public class PersonTest {

    @Test
    @Parameters({
            "17, false",
            "22, true" })
    public void isAdultAgeDirect(int age, boolean valid) throws Exception {
        assertThat(new Person(age).isAdult()).isEqualTo(valid);
    }

    @Test
    @Parameters(method = "adultValues")
    public void isAdultAgeDefinedMethod(int age, boolean valid) throws Exception {
        assertThat(new Person(age).isAdult()).isEqualTo(valid);
    }

    private Object[] adultValues() {
        return $($(17, false),
            $(22, true));
    }

    @Test
    @Parameters
    public void isAdultAgeDefaultMethod(int age, boolean valid) throws Exception {
        assertThat(new Person(age).isAdult()).isEqualTo(valid);
    }

    @SuppressWarnings("unused")
    private Object[] parametersForIsAdultAgeDefaultMethod() {
        return adultValues();
    }

    @Test
    @Parameters(source = PersonProvider.class)
    public void personIsAdult(Person person, boolean valid) {
        assertThat(person.isAdult()).isEqualTo(valid);
    }

    public static class PersonProvider {
        public static Object[] provideAdults() {
            return $(
                $(new Person(25), true),
                $(new Person(32), true));
        }

        public static Object[] provideTeens() {
            return $(
                $(new Person(12), false),
                $(new Person(17), false));
        }
    }

    @Test
    @Parameters(method = "adultValues")
    @TestCaseName("Is person with age {0} adult? It's {1} statement.")
    public void isAdultWithCustomTestName(int age, boolean valid) throws Exception {
        assertThat(new Person(age).isAdult()).isEqualTo(valid);
    }

    public static class Person {
        private int age;

        public Person(Integer age) {
            this.age = age;
        }

        public Person(String name, Integer age) {
            this.age = age;
        }

        public boolean isAdult() {
            return age >= 18;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "Person of age: " + age;
        }
    }
}
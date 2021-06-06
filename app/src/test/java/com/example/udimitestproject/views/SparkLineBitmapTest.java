package com.example.udimitestproject.views;

import android.content.Context;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class SparkLineBitmapTest extends TestCase {
    List<String> listNumbers;
    List<Float> answer;
    Context context;

    @Before
    public void setUp() {
        context = Mockito.mock(Context.class);
        answer = new ArrayList<>();
        listNumbers = new ArrayList<>();

        for(int i = 0; i < 50; ++i) {
            Float rand = (float) Math.random();
            answer.add(rand);
            listNumbers.add(String.valueOf(rand));
        }
    }

    @Test
    public void testSparkLineBitmap_getSparkLines_correct() {
        SparkLineBitmap sparkLineDrawer = new SparkLineBitmap(listNumbers, context, -1);
        List<Float> list = sparkLineDrawer.getSparkLines();

        for (float i: list) {
            assertTrue(i <= list.size());
            assertTrue(i >= 0);
        }
    }

    @Test
    public void testSparkLineBitmap_parseNumber_correct() {
        assertEquals(SparkLineBitmap.parseNumber("10.5"), 10.5f);
        assertEquals(SparkLineBitmap.parseNumber("10"), 10f);
        assertEquals(SparkLineBitmap.parseNumber("0"), 0f);
        assertEquals(SparkLineBitmap.parseNumber("-11110"), -11110f);
        assertEquals(SparkLineBitmap.parseNumber("-11110.66666"), -11110.66666f);
        //assertEquals(SparkLineBitmap.parseNumber("10,5"), 0f);
        //assertEquals(SparkLineBitmap.parseNumber("bullshit"), 0f);
    }

    @Test
    public void testSparkLineBitmap_convertStringArrayToNumbers_correct() {
        List<Float> numbers = SparkLineBitmap.convertStringArrayToNumbers(listNumbers);
        for(int i = 0; i < numbers.size(); ++i)
            assertEquals(numbers.get(i), answer.get(i));
    }
}
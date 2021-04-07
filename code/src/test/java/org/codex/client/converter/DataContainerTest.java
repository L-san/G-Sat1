package org.codex.client.converter;

import org.testng.annotations.Test;

public class DataContainerTest {
    DataContainer container = new DataContainer(new double[]{0,0,1}, new double[]{0.7,0,0.7},new double[]{0,0,0});

    @Test
    public void testTestToString() {
       //System.out.println(container);
    }
}
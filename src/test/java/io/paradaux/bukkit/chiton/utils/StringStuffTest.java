package io.paradaux.bukkit.chiton.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class StringStuffTest {

    @BeforeEach
    void setUp() {
        Logger logger = LoggerFactory.getLogger(StringStuffTest.class);
    }

    @Test
    void getFormattedTime() {

        System.out.println(StringStuff.getFormattedTime(456456343346L));

    }
}
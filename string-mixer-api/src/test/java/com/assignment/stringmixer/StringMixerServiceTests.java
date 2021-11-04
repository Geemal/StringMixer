package com.assignment.stringmixer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.assignment.stringmixer.services.StringMixerService;
import com.assignment.stringmixer.services.StringMixerServiceImpl;

import org.junit.jupiter.api.Test;

public class StringMixerServiceTests {

    @Test
    public void testMix() {
        String s1 = "my&friend&Paul has heavy hats! &";
        String s2 = "my friend John has many many friends &";

        String s3 = "mmmmm m nnnnn y&friend&Paul has heavy hats! &";
        String s4 = "my frie n d Joh n has ma n y ma n y frie n ds n&";

        StringMixerService stringMixerService = new StringMixerServiceImpl();

        Object result1 = stringMixerService.mix(s1, s2);
        Object result2 = stringMixerService.mix(s3, s4);

        assertEquals(result1, "2:nnnnn/1:aaaa/1:hhh/2:mmm/2:yyy/2:dd/2:ff/2:ii/2:rr/=:ee/=:ss");
        assertEquals(result2, "1:mmmmmm/=:nnnnnn/1:aaaa/1:hhh/2:yyy/2:dd/2:ff/2:ii/2:rr/=:ee/=:ss");
    }
}

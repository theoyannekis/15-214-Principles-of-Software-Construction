package edu.cmu.cs.cs214.hw6;

import org.junit.Before;
import org.junit.Test;

public class ArgParserTests {
    private String[] args;
    private String[] args2;
    private String[] args3;
    private String[] args4;
    private String[] args5;

    @Before
    public void setup() {
        args = new String[]{"../../.git", "../../.git", "10"};
        args2 = new String[]{"../../.gi", "../../.git", "10"};
        args3 = new String[]{"../../.git", "../../.git", "hi"};
        args4 = new String[]{"../../.git", "../../.git"};
        args5 = new String[]{"../../.git", "10", "../../.git"};

    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotEnoughArgs() {
        ArgParser.parseArgs(args4, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadPath() {
        ArgParser.parseArgs(args2, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotIntForN() {
        ArgParser.parseArgs(args3, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongArgsOrder() {
        ArgParser.parseArgs(args5, false);
    }


    @Test
    public void testValidArgs() {
        ArgParser.parseArgs(args,true);
    }
}

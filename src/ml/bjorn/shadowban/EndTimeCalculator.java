package ml.bjorn.shadowban;

import com.google.common.collect.ImmutableSet;

import java.time.Instant;
import java.util.Set;

class EndTimeCalculator {
    private static final Set<String> units = ImmutableSet.of("d", "h", "m", "s");

    static long get(String input){
        Long length;
        try {
            length = Long.parseLong(input.substring(0, input.length() - 1));
        } catch (Throwable error) {
            return 0;
        }
        String unit = input.substring(input.length() - 1);
        if(!units.contains(unit)){
            return 0;
        }
        switch(unit){
            case "d": length = length * 24 * 60 * 60 * 1000; break;
            case "h": length = length * 60 * 60 * 1000; break;
            case "m": length = length * 60 * 1000; break;
            case "s": length = length * 1000; break;
        }
        return Instant.now().toEpochMilli() + length;
    }
}

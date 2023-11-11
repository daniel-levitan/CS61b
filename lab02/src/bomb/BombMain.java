package bomb;

import common.IntList;

public class BombMain {
    public static void main(String[] args) {
        int phase = 2;
        if (args.length > 0) {
            phase = Integer.parseInt(args[0]);
        }
        // TODO: Find the correct passwords to each phase using debugging techniques
        Bomb b = new Bomb();
        if (phase >= 0) {
//            b.phase0("Figure this out. I wonder where the phases are defined...");
            b.phase0("39291226");
        }
        if (phase >= 1) {
//            b.phase1(null); // Figure this out too
            b.phase1(IntList.of(0, 9, 3, 0, 8)); // Figure this out too
        }
        if (phase >= 2) {
            String my_password = new String();
            for (int i = 0; i < 1337; i++)
                my_password += "1 ";
            my_password += "-81201430";
            b.phase2(my_password);
//            b.phase2("Figure this out. I wonder where the phases are defined...");
        }
    }
}

// First-Party Imports
import java.util.Arrays;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

// Third-Party Imports
import org.junit.Test;
import static org.junit.Assert.*;

// Local Imports
import com.caffeine.logic.Utils;

public class UtilsTest {

    @Test
    public void testValidateFEN(){
        ArrayList<String> validFENs = new ArrayList<String>();
        // Generated with http://bernd.bplaced.net/fengenerator/fengenerator.html?1,2,2,2,8,1,2,2,2,8,32
        validFENs.add("4n2n/1P4NP/1P3Pp1/Bp1rpP1p/2PNPPQK/1pRp3B/pkq1b1p1/4r1bR w - - 0 1");
        validFENs.add("2n3R1/1P1pPPp1/K1pPbk2/1B1Pqp1p/1PnbQ3/B1pp1PN1/3prPNR/5r2 w - - 0 1");
        validFENs.add("5rn1/p2PBpPp/5PR1/bKP1P2p/2nPk1rR/1bp1P1Qp/pN3Pp1/1q1N1B2 w - - 0 1");
        validFENs.add("k4r1b/2Prpp1p/K3NPpB/1R2Pp2/npB3P1/2P1Rq2/1pPPPN1p/3bQ1n1 w - - 0 1");
        validFENs.add("4R1B1/1kBpPn1P/p2ppNP1/b1q5/Rp1pP1Kp/n2P2P1/Qpr1PP2/1r1b1N2 w - - 0 1");
        validFENs.add("5k1b/1n1K1p2/pPr1r1q1/pP2Bpp1/NN1ppp2/Q1P2P1P/P2PP2R/Rbn4B w - - 0 1");
        validFENs.add("1r6/1nRKpkB1/P2pnpq1/1P6/1pP1P1P1/rPRPN1pB/ppP3bp/b4QN1 w - - 0 1");
        validFENs.add("rB6/P4npk/pPBP2Nq/1P3Kp1/Prp1p1PP/Q1RPpNbp/2b1p3/R4n2 w - - 0 1");
        validFENs.add("2b1nB1b/rqk1p3/ppP1nP1K/pB3RPP/3pP1P1/Q1p3p1/1P1rPNNp/4R3 w - - 0 1");
        validFENs.add("5R2/1ppqk1NP/bNR1npQ1/K5Pp/1PP1r3/1Pp3P1/ppp1PnP1/B1b1rB2 w - - 0 1");
        validFENs.add("brn3rb/1nB3p1/K1P2P2/pQ1ppRR1/p2NkpB1/1PP1P1Pp/1Ppq2PN/8 w - - 0 1");
        validFENs.add("b7/p3pR1p/pPN1kP2/1PPn3Q/2Pp2pr/1Pp3nP/1P1qNKpB/bB1rR3 w - - 0 1");
        validFENs.add("1R1Bb3/PPp3p1/1b1pp1p1/n3R1Pp/qrPNBN1K/1nrP3P/3pQPpP/2k5 w - - 0 1");
        validFENs.add("B2K1RQ1/r1pPP3/1r3P2/1P2B1pn/p1pR2Pb/1P1P2PN/pnppq1p1/3N1k1b w - - 0 1");
        validFENs.add("N3q2k/PP1pBBnP/p5P1/bQPP1n1p/R3P1p1/p1RKp1Np/rp2rPb1/8 w - - 0 1");
        validFENs.add("5RKb/4PNPp/pP1p1QpN/n1B2P1P/b2rP3/R1p1p1kB/P2P2pp/q1nr4 w - - 0 1");
        validFENs.add("r1R5/1PPp1PN1/1P1bP1bp/p2P4/1p1pqnBQ/k1p2p1P/p1n1PK2/1r1RB2N w - - 0 1");
        validFENs.add("Nb3K1B/Rn4Qp/3kp1P1/NPpP1bP1/r1PpB1Pp/2pn1Pq1/2p1P1pr/R7 w - - 0 1");
        validFENs.add("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        validFENs.add("4RR2/2p1PB1p/4Pp1B/Nn1r3p/2PP1Kpb/qpPPP2r/Q1P1b1pp/n4k1N w - d6 0 1");
        validFENs.add("Nn1R1n2/2RPqPP1/bpp4p/2pN1P1P/P3pr1p/2b1Pkp1/Br1P2pQ/B5K1 w - a3 0 1");
        validFENs.add("q2b2R1/1ppkppKR/PP2r1np/B1P2N2/PQ1p3p/3P2np/PPB1P1b1/1r2N3 w - h3 0 1");
        ArrayList<String> invalidFENs = new ArrayList<String>();
        invalidFENs.add("rnbqkZnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b QK - 0 1"); // Field 1
        invalidFENs.add("rnbqkbnr/ppp3pppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"); // Field 1
        invalidFENs.add("rnbqkbnr/pppppppp/6/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"); // Field 1
        invalidFENs.add("Nb3K1B/Rn4Qp/3kp1P1/NPpP1bP2/r1PpB1Pp/2pn1Pq1/2p1P1pr/R7 w - - 0 1"); // Field 1
        invalidFENs.add("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR - QK a1 0 1"); // Field 2
        invalidFENs.add("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR W KQkq - 0 1"); // Field 2
        invalidFENs.add("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR x KQkq - 0 1"); // Field 2
        invalidFENs.add("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b Q- - 0 1"); // Field 3
        invalidFENs.add("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b Qt - 0 1"); // Field 3
        invalidFENs.add("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkkq - 0 1"); // Field 3
        invalidFENs.add("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b QK y5 500 1"); // Field 4
        invalidFENs.add("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b QK -5 500 1"); // Field 4
        invalidFENs.add("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b QK a0 500 1"); // Field 4
        invalidFENs.add("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - -1 0"); // Field 5
        invalidFENs.add("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - int 0"); // Field 5
        invalidFENs.add("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - word 0"); // Field 5
        invalidFENs.add("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 -1"); // Field 6
        invalidFENs.add("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 int"); // Field 6
        invalidFENs.add("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 word"); // Field 6

        boolean allIsWell = true;
        // If any FEN in valid set reads FALSE, not all is well.
        for (String fen : validFENs){
            if (!Utils.isValidFEN(fen)){
                allIsWell = false;
                System.out.println("## "+fen);
            }
        }
        // If any FEN in invalid set reads TRUE, not all is well.
        for (String fen : invalidFENs){
            if (Utils.isValidFEN(fen)){
                allIsWell = false;
                System.out.println("## "+fen);
            }
        }

        assertTrue(allIsWell);
    }

    @Test
    public void testValidateBoardPosition(){
        String position;

        // Test all the board squares!
        for (int i = 1; i <= 8; i++){
            for (int j = 65; j <= 72; j++){
                position = String.format("%c%d", (char) j, i);

                if (!Utils.isValidBoardPosition(position)){ fail(); }
            }
        }

        // Test random invalid board squares
        for (int i = 0; i < 64; i++){
            position = String.format(
                "%c%d",
                (char) ThreadLocalRandom.current().nextInt(65, 90 + 1),
                ThreadLocalRandom.current().nextInt(9, 100 + 1)
            );
            if (Utils.isValidBoardPosition(position)){ fail(); }
        }

        // Test both chars aren't digits
        for (int i = 0; i < 64; i++){
            position = String.format(
                "%d%d",
                ThreadLocalRandom.current().nextInt(0, 9 + 1),
                ThreadLocalRandom.current().nextInt(0, 9 + 1)
            );
            if (Utils.isValidBoardPosition(position)){ fail(); }
        }

        // Test both chars aren't letters
        for (int i = 0; i < 64; i++){
            position = String.format(
                "%c%c",
                (char) ThreadLocalRandom.current().nextInt(65, 90 + 1),
                (char) ThreadLocalRandom.current().nextInt(65, 90 + 1)
            );
            if (Utils.isValidBoardPosition(position)){ fail(); }
        }
    }

    @Test
    public void testValidateMove(){
        // Test a few valids
        if (!Utils.isValidMove("a2a3")){ fail(); }
        if (!Utils.isValidMove("h5b6")){ fail(); }
        if (!Utils.isValidMove("g4a3")){ fail(); }
        if (!Utils.isValidMove("e1f8")){ fail(); }
        if (!Utils.isValidMove("c5a3")){ fail(); }

        // Test a few invalids
        if ( Utils.isValidMove("z2a3")){ fail(); }
        if ( Utils.isValidMove("q1bb")){ fail(); }
        if ( Utils.isValidMove("a5q0")){ fail(); }
        if ( Utils.isValidMove("g9r3")){ fail(); }
        if ( Utils.isValidMove("d0a3")){ fail(); }
    }

    @Test
    public void testTranslateBoardPositionToIndices(){
        if (!Arrays.equals(Utils.translate("a1"), new Integer[]{ 0,0 })){ fail(); }
        if (!Arrays.equals(Utils.translate("b2"), new Integer[]{ 1,1 })){ fail(); }
        if (!Arrays.equals(Utils.translate("C3"), new Integer[]{ 2,2 })){ fail(); }
        if (!Arrays.equals(Utils.translate("D4"), new Integer[]{ 3,3 })){ fail(); }
        if (!Arrays.equals(Utils.translate("e5"), new Integer[]{ 4,4 })){ fail(); }
        if (!Arrays.equals(Utils.translate("f6"), new Integer[]{ 5,5 })){ fail(); }
        if (!Arrays.equals(Utils.translate("G7"), new Integer[]{ 6,6 })){ fail(); }
        if (!Arrays.equals(Utils.translate("H8"), new Integer[]{ 7,7 })){ fail(); }
        if (!Arrays.equals(Utils.translate("a8"), new Integer[]{ 7,0 })){ fail(); }
        if (!Arrays.equals(Utils.translate("b4"), new Integer[]{ 3,1 })){ fail(); }
        if (!Arrays.equals(Utils.translate("c2"), new Integer[]{ 1,2 })){ fail(); }
        if (!Arrays.equals(Utils.translate("d1"), new Integer[]{ 0,3 })){ fail(); }
    }

    @Test
    public void testTranslateIndicesToBoardPosition(){
        System.out.println("##### "+Utils.translate(new Integer[]{7,0}));
        if (!Utils.translate(new Integer[]{ 0,0 }).equals("a1")){ fail(); }
        if (!Utils.translate(new Integer[]{ 1,1 }).equals("b2")){ fail(); }
        if (!Utils.translate(new Integer[]{ 2,2 }).equals("c3")){ fail(); }
        if (!Utils.translate(new Integer[]{ 3,3 }).equals("d4")){ fail(); }
        if (!Utils.translate(new Integer[]{ 4,4 }).equals("e5")){ fail(); }
        if (!Utils.translate(new Integer[]{ 5,5 }).equals("f6")){ fail(); }
        if (!Utils.translate(new Integer[]{ 6,6 }).equals("g7")){ fail(); }
        if (!Utils.translate(new Integer[]{ 7,7 }).equals("h8")){ fail(); }
        if (!Utils.translate(new Integer[]{ 7,0 }).equals("a8")){ fail(); }
        if (!Utils.translate(new Integer[]{ 3,1 }).equals("b4")){ fail(); }
        if (!Utils.translate(new Integer[]{ 1,2 }).equals("c2")){ fail(); }
        if (!Utils.translate(new Integer[]{ 0,3 }).equals("d1")){ fail(); }
    }

}

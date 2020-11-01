package Summation;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestJunit {
   @Test
	
   public void testAdd() {
	  int parallelTotal  = IndexSum.total; //grand thread total
      int returnedValue  = IndexSum.threadTotal; //partial threat total
      returnedValue *= 8; //Multiplies each thread count by 8(total number of threads)
      assertEquals(parallelTotal,returnedValue);
   }
}
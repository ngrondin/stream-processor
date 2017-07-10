import com.nic.streamprocessor.Node;
import com.nic.streamprocessor.Pipe;
import com.nic.streamprocessor.StreamTarget;
import com.nic.streamprocessor.nodes.Arithmetic;


public class Test
{

	public static void main(String[] args)
	{
		TestSource ts  = new TestSource();
		StreamTarget st = new StreamTarget(new String[] {"counter", "sum"});
		Arithmetic a1 = new Arithmetic();
		new Pipe(ts, "counter", st, "counter");
		new Pipe(ts, "constant", a1, "term1");
		new Pipe(ts, "counter", a1, "term2");
		new Pipe(a1, "result", st, "sum");
		while(ts.nextStep());
	}
}

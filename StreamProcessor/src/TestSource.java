import com.nic.streamprocessor.StreamSource;


public class TestSource extends StreamSource
{
	protected int counter;
	
	public TestSource()
	{
		counter = 0;
	}
	
	public boolean nextStep()
	{
		if(counter < 10)
		{
			setOutput("constant", 3.1f);
			setOutput("counter", (float)counter);
			pushOutputs();
			counter++;
			return true;
		}
		else
		{
			return false;
		}
	}
	
}

package bioinfo.comaWebServer.components;

import org.apache.tapestry5.annotations.Parameter;

import bioinfo.comaWebServer.entities.Output;

public class ViewOutput 
{	
	@Parameter(required = true)
	private Output output;
	
	@Parameter(required = true)
	private boolean showOutput;


	public Output getOutput() {
		return output;
	}

	public void setOutput(Output output) {
		this.output = output;
	}

	public boolean isShowOutput() {
		return showOutput;
	}

	public void setShowOutput(boolean showOutput) {
		this.showOutput = showOutput;
	}

}

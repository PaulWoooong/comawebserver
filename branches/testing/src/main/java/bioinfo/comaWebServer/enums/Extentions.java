package bioinfo.comaWebServer.enums;

public enum Extentions 
{
	INPUT_COMA  		(".fa"),
	OUTPUT_COMA_OUT 	(".out"),
	OUTPUT_COMA_ID 		(".ids"),
	OUTPUT_COMA_MA 		(".ina"),
	
	INPUT_MODELLER  	(".aln"),
	KEY_MODELLER  		(".key"),
	OUTPUT_MODELLER 	(".pdb"),
	
	INPUT_MSA_FA 		(".fa"),
	INPUT_MSA_COMA 		(".out"),
	OUTPUT_MSA 			(".out.fa"),
	
	PARAMS 	(".par"),
	LOG 	(".log"),
	ERR 	(".err");

	private final String extention;
	
	Extentions(String ext)
	{
		this.extention = ext;
	}
	
	public String getExtention()
	{
		return extention;
	}
}

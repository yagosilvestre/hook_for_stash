package versao.enuns;

public enum Branch {
	BRANCH_1 ("release"), BRANCH_2("hotfix"), BRANCH_3("develop");
	
	private final String branch;
	
	private Branch(String nome){
		branch = nome;
	}
	
	public String getBranch() {
		return branch;
	}
	
}

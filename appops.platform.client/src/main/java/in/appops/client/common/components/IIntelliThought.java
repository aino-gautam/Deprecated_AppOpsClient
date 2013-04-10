package in.appops.client.common.components;

public interface IIntelliThought {
	
	String getIntelliText();
	void setIntelliText(String intelliText);
	
	String getIntelliHtml();
	void setIntelliHtml(String intelliHtml);
	
	// More methods to provide List<User Entity>, List<Space Entity>, List<AllEntities>
	
	class IntelliThought implements IIntelliThought{
		private String intelliText;
		private String intelliHtml;

		@Override
		public String getIntelliText() {
			return intelliText;
		}

		@Override
		public void setIntelliText(String intelliText) {
			this.intelliText = intelliText;
		}

		@Override
		public String getIntelliHtml() {
			return intelliHtml;
		}

		@Override
		public void setIntelliHtml(String intelliHtml) {
			this.intelliHtml = intelliHtml;
		}
	}
}

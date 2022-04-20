package pageUIs.nopcommerce;

public class NopCommerceBasePageUI {
	public static final String DYNAMIC_LINK_BY_NAME = "//a[text()='%s']";
	public static final String DYNAMIC_BUTTON_BY_NAME = "//div[@class='buttons']//button[text()='%s']";
	public static final String DYNAMIC_MESSAGE_BY_TEXT = "//span[@id='%s' and text()='%s']";
	public static final String DYNAMIC_TEXTBOX_BY_ID = "//input[@id='%s']";
	public static final String EMAIL_ERROR_MSG = "//div[@class='%s' and contains(string(),'%s')]";
	public static final String DYNAMIC_MY_ACCOUNT_PAGE_BY_NAME = "//div[@class='listbox']//a[contains(string(),'%s')]";
	public static final String DYNAMIC_RADIO_BTN = "//input[@id='%s']";
	public static final String DYNAMIC_DROPDOWN_BY_TEXT = "//select[@name='%s']";
	public static final String DYNAMIC_INFO_BY_CLASS_AND_TEXT = "//li[@class='%s' and contains(string(),'%s')]";
	public static final String DYNAMIC_PRODUCT_BY_NAME = "//h2[@class='product-title' and contains(string(),'%s')]";
	public static final String DYNAMIC_REVIEW_TITLE_CONTENT = "//div[@class='%s' and contains(string(),'%s')]";
	public static final String DYNAMIC_FOOTER_LINK_BY_NAME = "//div[@class='footer']//a[text()='%s']";
	public static final String DYNAMIC_SEARCH_CHECKBOX = "//input[@type='checkbox' and @id='%s']";
	public static final String DYNAMIC_HEADER_LINK_BY_NAME = "//ul[@class='top-menu notmobile']//a[text()='%s']";
}

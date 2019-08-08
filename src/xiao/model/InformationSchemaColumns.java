package xiao.model;

/**
 * ���ݿ���Ӧ���ֶ���Ϣ�����ڲ�ѯ��֪��������ֶ���Ϣ
 * @author xjl
 * 2019-06-05 09:59:04
 */
public class InformationSchemaColumns {
	
	private String tableCatalog;
	
	/**
	 * �������ݿ�
	 */
	private String tableSchema;
	
	/**
	 * ������
	 */
	private String tableName;
	
	/**
	 * �ֶ���
	 */
	private String columnName;
	
	private Integer ordinalPosition;
	
	/**
	 * �ֶ�Ĭ��ֵ
	 */
	private String columnDefault;
	
	/**
	 * �Ƿ���Կգ�Ĭ�ϡ�YES��������NO��
	 */
	private String isNullable;
	
	/**
	 * ��������
	 */
	private String dataType;
	
	private Long characterMaximumLength;
	
	private Long characterOctetLength;
	
	private Long NumericPrecision;
	
	private Long NumericScale;
	
	private Integer datetimePrecision;
	
	private String characterSetName;
	
	private String collationName;
	
	/**
	 * �ֶ�����
	 */
	private String columnType;
	
	/**
	 * �ֶ�Լ��
	 */
	private String columnKey;
	
	private String extra;
	
	/**
	 * �ֶ�Ȩ��
	 */
	private String privileges;
	
	/**
	 * �ֶα�ע
	 */
	private String columnComment;
	
	private String generationExpression;
	
	private Integer srsId;

	public String getTableCatalog() {
		return tableCatalog;
	}

	public void setTableCatalog(String tableCatalog) {
		this.tableCatalog = tableCatalog;
	}

	public String getTableSchema() {
		return tableSchema;
	}

	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Integer getOrdinalPosition() {
		return ordinalPosition;
	}

	public void setOrdinalPosition(Integer ordinalPosition) {
		this.ordinalPosition = ordinalPosition;
	}

	public String getColumnDefault() {
		return columnDefault;
	}

	public void setColumnDefault(String columnDefault) {
		this.columnDefault = columnDefault;
	}

	public String getIsNullable() {
		return isNullable;
	}

	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Long getCharacterMaximumLength() {
		return characterMaximumLength;
	}

	public void setCharacterMaximumLength(Long characterMaximumLength) {
		this.characterMaximumLength = characterMaximumLength;
	}

	public Long getCharacterOctetLength() {
		return characterOctetLength;
	}

	public void setCharacterOctetLength(Long characterOctetLength) {
		this.characterOctetLength = characterOctetLength;
	}

	public Long getNumericPrecision() {
		return NumericPrecision;
	}

	public void setNumericPrecision(Long numericPrecision) {
		NumericPrecision = numericPrecision;
	}

	public Long getNumericScale() {
		return NumericScale;
	}

	public void setNumericScale(Long numericScale) {
		NumericScale = numericScale;
	}

	public Integer getDatetimePrecision() {
		return datetimePrecision;
	}

	public void setDatetimePrecision(Integer datetimePrecision) {
		this.datetimePrecision = datetimePrecision;
	}

	public String getCharacterSetName() {
		return characterSetName;
	}

	public void setCharacterSetName(String characterSetName) {
		this.characterSetName = characterSetName;
	}

	public String getCollationName() {
		return collationName;
	}

	public void setCollationName(String collationName) {
		this.collationName = collationName;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getColumnKey() {
		return columnKey;
	}

	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getPrivileges() {
		return privileges;
	}

	public void setPrivileges(String privileges) {
		this.privileges = privileges;
	}

	public String getColumnComment() {
		return columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	public String getGenerationExpression() {
		return generationExpression;
	}

	public void setGenerationExpression(String generationExpression) {
		this.generationExpression = generationExpression;
	}

	public Integer getSrsId() {
		return srsId;
	}

	public void setSrsId(Integer srsId) {
		this.srsId = srsId;
	}

	@Override
	public String toString() {
		return "InformationSchemaColumns [tableCatalog=" + tableCatalog + ", tableSchema=" + tableSchema
				+ ", tableName=" + tableName + ", columnName=" + columnName + ", ordinalPosition=" + ordinalPosition
				+ ", columnDefault=" + columnDefault + ", isNullable=" + isNullable + ", dataType=" + dataType
				+ ", characterMaximumLength=" + characterMaximumLength + ", characterOctetLength="
				+ characterOctetLength + ", NumericPrecision=" + NumericPrecision + ", NumericScale=" + NumericScale
				+ ", datetimePrecision=" + datetimePrecision + ", characterSetName=" + characterSetName
				+ ", collationName=" + collationName + ", columnType=" + columnType + ", columnKey=" + columnKey
				+ ", extra=" + extra + ", privileges=" + privileges + ", columnComment=" + columnComment
				+ ", generationExpression=" + generationExpression + ", srsId=" + srsId + "]";
	}
	
	
}

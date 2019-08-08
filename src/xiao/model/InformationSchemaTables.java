package xiao.model;

import java.util.Date;

/**
 * 数据库表信息，用于查询所有表的配置信息
 * @author xjl
 * 2019-06-05 10:00:47
 */
public class InformationSchemaTables {
	
	private String tableCatalog;
	
	/**
	 * 所属数据库
	 */
	private String tableSchema;
	
	/**
	 * 表名
	 */
	private String tableName;
	
	
	/**
	 * 表类型
	 */
	private String tableType;
	
	/**
	 * 引擎
	 */
	private String engine;
	
	/**
	 * 版本
	 */
	private Integer version;
	
	private String rowFormat;
	
	/**
	 * 表已存数据条数
	 */
	private Long tableRows;
	
	private Long avgRowLength;
	
	private Long dataLength;
	
	private Long maxDataLength;
	
	private Long indexLength;
	
	private Long dataFree;
	
	private Long autoIncrement;
	
	/**
	 * 表创建时间
	 */
	private Date createTime;
	
	/**
	 * 表修改时间
	 */
	private Date updateTime;
	
	private Date checkTime;
	
	private String tableCollation;
	
	private Long checksum;
	
	private String createOptions;
	
	/**
	 * 表描述
	 */
	private String tableComment;

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

	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getRowFormat() {
		return rowFormat;
	}

	public void setRowFormat(String rowFormat) {
		this.rowFormat = rowFormat;
	}

	public Long getTableRows() {
		return tableRows;
	}

	public void setTableRows(Long tableRows) {
		this.tableRows = tableRows;
	}

	public Long getAvgRowLength() {
		return avgRowLength;
	}

	public void setAvgRowLength(Long avgRowLength) {
		this.avgRowLength = avgRowLength;
	}

	public Long getDataLength() {
		return dataLength;
	}

	public void setDataLength(Long dataLength) {
		this.dataLength = dataLength;
	}

	public Long getMaxDataLength() {
		return maxDataLength;
	}

	public void setMaxDataLength(Long maxDataLength) {
		this.maxDataLength = maxDataLength;
	}

	public Long getIndexLength() {
		return indexLength;
	}

	public void setIndexLength(Long indexLength) {
		this.indexLength = indexLength;
	}

	public Long getDataFree() {
		return dataFree;
	}

	public void setDataFree(Long dataFree) {
		this.dataFree = dataFree;
	}

	public Long getAutoIncrement() {
		return autoIncrement;
	}

	public void setAutoIncrement(Long autoIncrement) {
		this.autoIncrement = autoIncrement;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getTableCollation() {
		return tableCollation;
	}

	public void setTableCollation(String tableCollation) {
		this.tableCollation = tableCollation;
	}

	public Long getChecksum() {
		return checksum;
	}

	public void setChecksum(Long checksum) {
		this.checksum = checksum;
	}

	public String getCreateOptions() {
		return createOptions;
	}

	public void setCreateOptions(String createOptions) {
		this.createOptions = createOptions;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}

	@Override
	public String toString() {
		return "InformationSchemaTables [tableCatalog=" + tableCatalog + ", tableSchema=" + tableSchema + ", tableName="
				+ tableName + ", tableType=" + tableType + ", engine=" + engine + ", version=" + version
				+ ", rowFormat=" + rowFormat + ", tableRows=" + tableRows + ", avgRowLength=" + avgRowLength
				+ ", dataLength=" + dataLength + ", maxDataLength=" + maxDataLength + ", indexLength=" + indexLength
				+ ", dataFree=" + dataFree + ", autoIncrement=" + autoIncrement + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", checkTime=" + checkTime + ", tableCollation=" + tableCollation
				+ ", checksum=" + checksum + ", createOptions=" + createOptions + ", tableComment=" + tableComment
				+ "]";
	}
	
	
}

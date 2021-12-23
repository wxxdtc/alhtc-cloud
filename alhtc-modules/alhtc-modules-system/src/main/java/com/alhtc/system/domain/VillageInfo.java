package com.alhtc.system.domain;

import com.alhtc.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 通知公告表 sys_notice
 *
 * @author alhtc
 */
public class VillageInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 公告ID
     */
    private Long infoId;

    /**
     * 公告标题
     */
    private String infoTitle;

    /**
     * 公告类型（1通知 2公告）
     */
    private String infoType;

    /**
     * 公告内容
     */
    private String infoContent;

    /**
     * 公告浏览数
     */
    private Long visitorVolume;

    /**
     * 公告状态（0正常 1关闭）
     */
    private String status;

    public Long getInfoId() {
        return infoId;
    }

    public void setInfoId(Long visitorVolume) {
        this.visitorVolume = visitorVolume;
    }

    public Long getVisitorVolume() {
        return visitorVolume;
    }

    public void setVisitorVolume(Long visitorVolume) {
        this.visitorVolume = visitorVolume;
    }

    public void setInfoTitle(String infoTitle) {
        this.infoTitle = infoTitle;
    }

    @NotBlank(message = "公告标题不能为空")
    @Size(min = 0, max = 50, message = "公告标题不能超过50个字符")
    public String getInfoTitle() {
        return infoTitle;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoContent(String infoContent) {
        this.infoContent = infoContent;
    }

    public String getInfoContent() {
        return infoContent;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("infoId", getInfoId())
                .append("infoTitle", getInfoTitle())
                .append("infoType", getInfoType())
                .append("infoContent", getInfoContent())
                .append("visitorVolume", getVisitorVolume())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}

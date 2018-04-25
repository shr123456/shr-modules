package com.kexin.dkt.core.entity.school;

public class SchoolCarousel {

  private String schoolCarouselId;
  private String schoolCarouselImg;
  private String basicRepositoryId;
  private long status;
  private long deleted;
  private String name;


  public String getSchoolCarouselId() {
    return schoolCarouselId;
  }

  public void setSchoolCarouselId(String schoolCarouselId) {
    this.schoolCarouselId = schoolCarouselId;
  }


  public String getSchoolCarouselImg() {
    return schoolCarouselImg;
  }

  public void setSchoolCarouselImg(String schoolCarouselImg) {
    this.schoolCarouselImg = schoolCarouselImg;
  }


  public String getBasicRepositoryId() {
    return basicRepositoryId;
  }

  public void setBasicRepositoryId(String basicRepositoryId) {
    this.basicRepositoryId = basicRepositoryId;
  }


  public long getStatus() {
    return status;
  }

  public void setStatus(long status) {
    this.status = status;
  }


  public long getDeleted() {
    return deleted;
  }

  public void setDeleted(long deleted) {
    this.deleted = deleted;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}

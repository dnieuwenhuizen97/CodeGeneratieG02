package io.swagger.model;

import org.springframework.data.domain.Page;

/**
 * @author SafeerAnsari
 * Created at 4:29 PM, 02/05/2020
 */

public class ResponseWrapper {
    private Object data;
    private PageInfo pageInfo;

    public ResponseWrapper(Page page) {
        this.data = page.getContent();
        this.pageInfo = new PageInfo(page);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public static class PageInfo {
        private Integer pageNumber;
        private Integer size;
        private Boolean empty;
        private Integer totalPages;
        private Long totalElements;
        private Integer numberOfElements;

        public PageInfo(Page page) {
            this.pageNumber = page.getNumber();
            this.size = page.getSize();
            this.empty = page.isEmpty();
            this.totalPages = page.getTotalPages();
            this.totalElements = page.getTotalElements();
            this.numberOfElements = page.getNumberOfElements();
        }

        public Integer getPageNumber() {
            return pageNumber;
        }

        public void setPageNumber(Integer pageNumber) {
            this.pageNumber = pageNumber;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

        public Boolean getEmpty() {
            return empty;
        }

        public void setEmpty(Boolean empty) {
            this.empty = empty;
        }

        public Integer getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(Integer totalPages) {
            this.totalPages = totalPages;
        }

        public Long getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(Long totalElements) {
            this.totalElements = totalElements;
        }

        public Integer getNumberOfElements() {
            return numberOfElements;
        }

        public void setNumberOfElements(Integer numberOfElements) {
            this.numberOfElements = numberOfElements;
        }
    }
}

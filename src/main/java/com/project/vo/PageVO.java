package com.project.vo;

public class PageVO {

		private int count;
		private int currentPage;
		private int contentsCount;
		private int pagesCount;
		
		public PageVO(int count, int currentPage, int contentsCount, int pagesCount) {
			this.count = count;
			this.currentPage = currentPage;
			this.contentsCount = contentsCount;
			this.pagesCount = pagesCount;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public int getCurrentPage() {
			return currentPage;
		}

		public void setCurrentPage(int currentPage) {
			this.currentPage = currentPage;
		}

		public int getContentsCount() {
			return contentsCount;
		}

		public void setContentsCount(int contentsCount) {
			this.contentsCount = contentsCount;
		}

		public int getPagesCount() {
			return pagesCount;
		}

		public void setPagesCount(int pagesCount) {
			this.pagesCount = pagesCount;
		}

		public int getTotalPage() {
			return count / contentsCount + (count % contentsCount == 0 ? 0 : 1);
		}
		
		public int getTotalPageGroup() {
			return getTotalPage() / pagesCount + (getTotalPage() % pagesCount == 0 ? 0:1);
		}
		
		public int getCurrentPageGroupNo() {
			return currentPage / pagesCount + (currentPage % pagesCount == 0 ? 0:1);
		}
		
		public int getStartPageOfPageGroup() {
			return (getCurrentPageGroupNo() - 1) * pagesCount + 1;
		}
		
		public int getEndPageOfPageGroup() {
			if(getCurrentPageGroupNo() * pagesCount > getTotalPage())
				return getTotalPage();
			return getCurrentPageGroupNo() * pagesCount;
		}
		
		public boolean isPreviousPageGroup() {
			return getCurrentPageGroupNo() > 1;
		}
		
		public boolean isNextPageGroup() {
			return getCurrentPageGroupNo() < getTotalPageGroup();
		}
		
		
		@Override
		public String toString() {
			return "pageVO [count=" + count + ", currentPage=" + currentPage + ", contentsCount=" + contentsCount
					+ ", pagesCount=" + pagesCount + "]";
		}
	
		
}

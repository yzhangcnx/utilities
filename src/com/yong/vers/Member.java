package com.yong.vers;


/**
 *  yongzhang
 *
 */

  public class Member 
  {
  	

		String memberId;
  	String fn;
  	String ln;
  	String memberPremium;
  	String memberPlanXref;
  	
  	Member()
  	{
  		this.memberId = "";
  		this.fn = "";
  		this.ln = "";
  		this.memberPremium = "";
  		this.memberPlanXref = "";
  	}
  	
  	public String getMemberId() {
			return memberId;
		}

		public void setMemberId(String memberId) {
			this.memberId = memberId;
		}

		public String getFn() {
			return fn;
		}

		public String getLn() {
			return ln;
		}

		public void setFn(String fn) {
			this.fn = fn;
		}

		public void setLn(String ln) {
			this.ln = ln;
		}
		
		public String getMemberPremium() {
			return memberPremium;
		}

		public void setMemberPremium(String memberPremium) {
			this.memberPremium = memberPremium;
		}

		public String getMemberPlanXref() {
			return memberPlanXref;
		}

		public void setMemberPlanXref(String memberPlanXref) {
			this.memberPlanXref = memberPlanXref;
		}
  }

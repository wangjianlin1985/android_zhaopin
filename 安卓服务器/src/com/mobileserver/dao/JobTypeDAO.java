package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.JobType;
import com.mobileserver.util.DB;

public class JobTypeDAO {

	public List<JobType> QueryJobType() {
		List<JobType> jobTypeList = new ArrayList<JobType>();
		DB db = new DB();
		String sql = "select * from JobType where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				JobType jobType = new JobType();
				jobType.setJobTypeId(rs.getInt("jobTypeId"));
				jobType.setTypeName(rs.getString("typeName"));
				jobTypeList.add(jobType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return jobTypeList;
	}
	/* 传入职位分类对象，进行职位分类的添加业务 */
	public String AddJobType(JobType jobType) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新职位分类 */
			String sqlString = "insert into JobType(typeName) values (";
			sqlString += "'" + jobType.getTypeName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "职位分类添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "职位分类添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除职位分类 */
	public String DeleteJobType(int jobTypeId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from JobType where jobTypeId=" + jobTypeId;
			db.executeUpdate(sqlString);
			result = "职位分类删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "职位分类删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据职位分类id获取到职位分类 */
	public JobType GetJobType(int jobTypeId) {
		JobType jobType = null;
		DB db = new DB();
		String sql = "select * from JobType where jobTypeId=" + jobTypeId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				jobType = new JobType();
				jobType.setJobTypeId(rs.getInt("jobTypeId"));
				jobType.setTypeName(rs.getString("typeName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return jobType;
	}
	/* 更新职位分类 */
	public String UpdateJobType(JobType jobType) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update JobType set ";
			sql += "typeName='" + jobType.getTypeName() + "'";
			sql += " where jobTypeId=" + jobType.getJobTypeId();
			db.executeUpdate(sql);
			result = "职位分类更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "职位分类更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}

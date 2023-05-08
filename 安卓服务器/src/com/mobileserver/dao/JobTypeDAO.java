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
	/* ����ְλ������󣬽���ְλ��������ҵ�� */
	public String AddJobType(JobType jobType) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����ְλ���� */
			String sqlString = "insert into JobType(typeName) values (";
			sqlString += "'" + jobType.getTypeName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "ְλ������ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ְλ�������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��ְλ���� */
	public String DeleteJobType(int jobTypeId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from JobType where jobTypeId=" + jobTypeId;
			db.executeUpdate(sqlString);
			result = "ְλ����ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ְλ����ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ����ְλ����id��ȡ��ְλ���� */
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
	/* ����ְλ���� */
	public String UpdateJobType(JobType jobType) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update JobType set ";
			sql += "typeName='" + jobType.getTypeName() + "'";
			sql += " where jobTypeId=" + jobType.getJobTypeId();
			db.executeUpdate(sql);
			result = "ְλ������³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ְλ�������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}

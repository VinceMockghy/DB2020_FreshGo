package xyz.zghy.freshgo.util;

/**
 * @author ghy
 * @date 2020/7/3 ����7:47
 */

public class DbException extends BaseException {
	public DbException(Throwable ex){
		super("���ݿ��������"+ex.getMessage());
	}
}

package xyz.zghy.freshgo.util;

public class DbException extends BaseException {
	public DbException(Throwable ex){
		super("���ݿ��������"+ex.getMessage());
	}
}

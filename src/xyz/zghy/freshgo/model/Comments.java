package xyz.zghy.freshgo.model;

/**
 * @author ghy
 * @date 2020/7/4 ����12:12
 */
public class Comments {
    private int userId;
    private int goodsId;
    private String commentMsg;
    private String commentDate;
    private int commentStar;
    //TODO ����ȱ��ͼƬ����


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getCommentMsg() {
        return commentMsg;
    }

    public void setCommentMsg(String commentMsg) {
        this.commentMsg = commentMsg;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public int getCommentStar() {
        return commentStar;
    }

    public void setCommentStar(int commentStar) {
        this.commentStar = commentStar;
    }
}

package com.saic.ebiz.vbox.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.saic.ebiz.vbox.conf.StreamConstant;
import com.saic.ebiz.vbox.stream.base.AbstractStream;
import com.saic.ebiz.vbox.stream.base.DownStream;
import com.saic.ebiz.vbox.stream.base.UpStream;
import com.saic.ebiz.vbox.utils.MongoDBUtil;
import com.saic.ebiz.vbox.utils.StreamUtils;

/**
 * 保存上行/下行数据到MongoDB
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月23日
 *
 */
@Sharable
@Component
public class MongoDBHandler extends ChannelDuplexHandler /*SimpleChannelInboundHandler<AbstractStream>*/{
	
	private static final Logger logger = LoggerFactory.getLogger(MongoDBHandler.class);
	
	@Autowired
	private MongoDBUtil mongoDBUtil;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	    logger.info("MongoDBHandler.channelRead接收到保存数据请求!");
	    if (msg instanceof AbstractStream) {
	        logger.info("MongoDBHandler.channelRead接收到保存AbstractStream格式的请求数据!");
	        AbstractStream abstractStream = (AbstractStream) msg;
            String sn = abstractStream.getSn();
            String commandType = abstractStream.getCommandType();
            logger.warn("MongoDBHandler.channelRead接收到的数据为commandType={},sn={}.",commandType,sn);
            if(msg instanceof UpStream){
                UpStream upStream = (UpStream) msg;
                logger.warn("MongoDBHandler.channelRead接收到UpStream格式的请求数据,保存到MongoDB!");
                try {
                    //保存具体的指令消息体内容
                    String collecName=StreamConstant.GPRS_MONGODB_TYPE_PREFIX+upStream.getCommandType();
                    mongoDBUtil.insert(upStream.toDbObject(), collecName);
                    logger.info("MongoDBHandler.channelRead保存上行指令消息体内容到[{}]结束!",collecName);
                    //保存完整的上行指令信息
                    mongoDBUtil.insert(upStream.toDbObject(),StreamConstant.GPRS_MONGODB_UP_COLLNAME);
                    logger.info("MongoDBHandler.channelRead保存上行指令信息到[gprs_upstream_history]结束!");
                } catch (Exception e) {
                    logger.error("MongoDBHandler.channelRead保存UpStream数据到MongoDB异常.commandType={},sn={}...{}",commandType,sn, e);
                }
            }else{
                logger.warn("MongoDBHandler.channelRead接收到的数据类型不是UpStream类型.");
                try {
                    //保存完整的错误指令信息
                    mongoDBUtil.insert(abstractStream.toDbObject(),StreamConstant.GPRS_MONGODB_ERRORE_COLLNAME);
                } catch (Exception e) {
                    logger.error("MongoDBHandler.channelRead保存UpStream数据到MongoDB异常.commandType={},sn={}...{}",commandType,sn, e);
                }
            }
            
            try {
                StreamUtils.printMessageEntityPrettyLog(msg);
            } catch (IllegalArgumentException | IllegalAccessException e1) {
                logger.error("MongoDBHandler.channelRead打印实体日志信息异常!",e1);
            }
	    }else{
            logger.warn("MongoDBHandler.channelRead接收到的数据类型不是AbstractStream类型,不做处理,继续传递.");
        }
		
	    //不释放对象，直接调用下一个Handler
	    ctx.fireChannelRead(msg);
	}
	
	/**
     * 保存下行AbstractStream数据
     */
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        logger.info("MongoDBHandler.write接收到保存数据请求!");
        if (msg instanceof AbstractStream) {
            AbstractStream abstractStream = (AbstractStream) msg;
            String sn = abstractStream.getSn();
            String commandType = abstractStream.getCommandType();
            logger.warn("MongoDBHandler.write接收到的数据为commandType={},sn={}.",commandType,sn);
            if(msg instanceof DownStream){
                DownStream downStream = (DownStream) msg;
                logger.warn("MongoDBHandler.write接收到的数据类型是DownStream类型.");
                logger.info("MongoDBHandler.write接收到DownStream格式的请求数据,保存到MongoDB!");
                try {
                    //保存独立的指令消息体内容
                    String collecName=StreamConstant.GPRS_MONGODB_TYPE_PREFIX+downStream.getCommandType();
                    mongoDBUtil.insert(downStream.toDbObject(),collecName);
                    logger.info("MongoDBHandler.write保存下行指令消息体内容到[{}]结束!",collecName);
                    //保存完整的下行指令信息
                    mongoDBUtil.insert(downStream.toDbObject(),StreamConstant.GPRS_MONGODB_DOWN_COLLNAME);
                    logger.info("MongoDBHandler.write保存下行指令消息体内容到[gprs_downstream_history]结束!");
                } catch (Exception e) {
                    logger.error("MongoDBHandler.write保存DownStream数据到MongoDB异常...", e);
                }
            }else{
                logger.warn("MongoDBHandler.write接收到的数据类型不是DownStream类型.");
                try {
                    //保存完整的错误指令信息
                    mongoDBUtil.insert(abstractStream.toDbObject(),StreamConstant.GPRS_MONGODB_ERRORE_COLLNAME);
                } catch (Exception e) {
                    logger.error("MongoDBHandler.write保存UpStream数据到MongoDB异常.commandType={},sn={}...{}",commandType,sn, e);
                }
            }
            
            try {
                StreamUtils.printMessageEntityPrettyLog(msg);
            } catch (IllegalArgumentException | IllegalAccessException e1) {
                logger.error("MongoDBHandler.channelRead打印实体日志信息异常!",e1);
            }
        }else{
            logger.warn("MongoDBHandler.write接收到的数据类型不是AbstractStream类型,不做处理,继续传递.");
            //不释放对象，直接传递
            super.write(ctx, msg, promise);
        }
        
    }
    
//  @Override
//  public void channelRead0(ChannelHandlerContext ctx, AbstractStream msg) throws Exception {
//      logger.info("接收到IMongoBean格式的请求数据!");
//      try {
//          //保存独立的指令消息体内容
//          mongoDBUtil.insert(msg.toDbObject(), 
//                  StreamConstant.GPRS_MONGODB_TYPE_PREFIX+msg.getCommandType());
//          if(msg instanceof UpStream){
//              logger.info("接收到UpStream格式的请求数据,保存到MongoDB!");
//              //保存完整的上行指令信息
//              mongoDBUtil.insert(msg.toDbObject(),StreamConstant.GPRS_MONGODB_UP_COLLNAME);
//          }else if(msg instanceof DownStream){
//              logger.info("接收到DownStream格式的请求数据,保存到MongoDB!");
//              //保存完整的下行指令信息
//              mongoDBUtil.insert(msg.toDbObject(),StreamConstant.GPRS_MONGODB_DOWN_COLLNAME);
//          }else{
//              logger.info("接收到非UpStream/DownStream错误格式的请求数据,保存到MongoDB!");
//              //保存完整的错误指令信息
//              mongoDBUtil.insert(msg.toDbObject(),StreamConstant.GPRS_MONGODB_ERRORE_COLLNAME);
//          }
//      } catch (Exception e) {
//          logger.error("保存数据到MongoDB异常...",e);
//      }
//      
//      try {
//          StreamUtils.printMessageEntityPrettyLog(msg);
//      } catch (IllegalArgumentException | IllegalAccessException e1) {
//          logger.error(".encode()打印实体日志信息异常!",e1);
//      }
//      
//      ctx.fireChannelRead(msg);
//  }

}
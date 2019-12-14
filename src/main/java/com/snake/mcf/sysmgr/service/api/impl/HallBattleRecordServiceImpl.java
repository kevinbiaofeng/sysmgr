package com.snake.mcf.sysmgr.service.api.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snake.mcf.common.service.ConfigResource;
import com.snake.mcf.common.utils.DateUtils;
import com.snake.mcf.common.utils.security.aes.AESCBCUtils;
import com.snake.mcf.sysmgr.mapper.api.HallBattleRecordMapper;
import com.snake.mcf.sysmgr.repertory.api.dto.battle.DrawScore;
import com.snake.mcf.sysmgr.repertory.api.dto.battle.GameKindItem;
import com.snake.mcf.sysmgr.repertory.api.dto.battle.RecordBackInfo;
import com.snake.mcf.sysmgr.repertory.api.dto.battle.RecordDrawInfo;
import com.snake.mcf.sysmgr.repertory.api.dto.battle.RoomAccountInfo;
import com.snake.mcf.sysmgr.repertory.api.dto.battle.RoomScore;
import com.snake.mcf.sysmgr.service.api.HallBattleRecordService;

@Service
public class HallBattleRecordServiceImpl implements HallBattleRecordService {

	@Autowired
    private HallBattleRecordMapper hallBattleRecordMapper; //约战、个人金币场战绩、亲友圈战绩
	
	@Autowired
    private ConfigResource configResource;
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getHallBattleRecord(Long userId, Integer type, Integer apiVersion) throws IllegalAccessException, InvocationTargetException {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("apiVersion", apiVersion);
		data.put("valid", true);
		data.put("dateTime", System.currentTimeMillis());
		if(type == 2) {
			List<Map<String, Object>> sList = null;
			List<List<?>> result = hallBattleRecordMapper.callGetHallBattleRecordTypeTwo(userId);
			if(result != null && result.size() > 0) {
				if(result.get(0) != null && result.get(0).size() > 0) {
					List<RecordDrawInfo> draw = (List<RecordDrawInfo>)result.get(1);
					List<GameKindItem> game = (List<GameKindItem>)result.get(2);
					List<DrawScore> dsCore = (List<DrawScore>) result.get(0);
					
					if(dsCore != null && dsCore.size() > 0) {
						sList = new ArrayList<Map<String, Object>>();
						Integer kindId = null;
						Long drawId = null;
						Date gameTime = null;
						Map<String, Object> scoreMap = null;
						for (DrawScore drawScore : dsCore) {
							scoreMap = new HashMap<String, Object>();
							kindId = drawScore.getKindId();
							drawId = drawScore.getDrawId();
							
							scoreMap.put("kindId", kindId);
							for (GameKindItem gameKindItem : game) {
								if(kindId.intValue() == gameKindItem.getKindId().intValue()) {
									scoreMap.put("kindName", gameKindItem.getKindName());
									break;
								}
							}
							
							gameTime = drawScore.getInsertTime();
							if(gameTime != null) {
								scoreMap.put("gameTime", DateUtils.format(gameTime, "yyyy-MM-dd HH:mm:ss"));
							}
							
							for (RecordDrawInfo drawInfo : draw) {
								if(drawId.longValue() == drawInfo.getDrawId().longValue()) {
									scoreMap.put("userCount", drawInfo.getUserCount());
									break;
								}
							}
							scoreMap.put("score", drawScore.getScore());
							sList.add(scoreMap);
						}
					}
					
				}
			}
			data.put("sList", sList);
		}else {
			List<List<?>> result = hallBattleRecordMapper.callGetHallBattleRecordTypeOne(userId);
			if(result != null && result.size() > 0) {
				List<Map<String, Object>> bList = null;
				
				if(result.get(0) != null && result.get(0).size() > 0) {
					List<RecordBackInfo> battleInfo = (List<RecordBackInfo>)result.get(0);
					List<GameKindItem> game = (List<GameKindItem>)result.get(1);
					List<RoomAccountInfo> user = (List<RoomAccountInfo>) result.get(2);
					List<RoomAccountInfo> host = (List<RoomAccountInfo>) result.get(3);
					List<RoomScore> battle = (List<RoomScore>) result.get(4);
					
					if(battle != null && battle.size() > 0) {
						bList = new ArrayList<Map<String, Object>>();//result list
						String nickName = null;
						Long roomHostId = null;
						Integer kindId = null;
						Integer roomId = null;
						Date startTime = null;
						List<Map<String, Object>> battleUserList = null;
						Map<String, Object> battleUser = null;
						Map<String, Object> scoreMap = null;
						List<Map<String, Object>> gamesNumList = null;
						Map<String, Object> gamesNumMap = null;
						Set<Integer> gamesnum = new HashSet<Integer>();;
						for (RoomScore roomScore : battle) {
							/** 获取战绩 **/
							scoreMap = new HashMap<String, Object>();
							kindId = roomScore.getKindId();
							roomHostId = roomScore.getRoomHostId();
							roomId = roomScore.getRoomId();
							scoreMap.put("kindId", roomScore.getKindId());
							if(game != null && game.size() > 0) {
								for (GameKindItem gameKindItem : game) {
									if(kindId.intValue() == gameKindItem.getKindId().intValue()) {
										scoreMap.put("kindName", gameKindItem.getKindName());
										break;
									}
								}
							}
							scoreMap.put("roomId", roomId);
							scoreMap.put("baseScore", roomScore.getCellScore());
							
							startTime = roomScore.getStartTime();
							if(startTime != null) {
								scoreMap.put("startTime", DateUtils.format(startTime, "yyyy-MM-dd HH:mm:ss"));
							}
							
							scoreMap.put("roomHostId", roomHostId);
							scoreMap.put("score", roomScore.getScore());
							
							if(roomHostId != null && roomHostId != 0) {
								if(host != null && host.size() > 0) {
									for (RoomAccountInfo ra : host) {
										if(ra.getUserId().longValue() == roomHostId.longValue()) {
											scoreMap.put("gameId", ra.getGameId());
											nickName = ra.getNickName();
											if(StringUtils.isNotEmpty(nickName)) {
												scoreMap.put("nickName", AESCBCUtils.decrypt(nickName, configResource.getAesUserKey()));
											}
										}
										break;
									}
								}
							}
							
							scoreMap.put("playBackCode", roomScore.getPlayBackCode());
							scoreMap.put("guid", roomScore.getPersonalRoomGuid());
							scoreMap.put("chairId", roomScore.getChairId());
							
							
							if(battleInfo != null && battleInfo.size() > 0) {
								gamesNumList = new ArrayList<Map<String, Object>>();
								gamesnum.clear();
								for (RecordBackInfo recordBackInfo : battleInfo) 
								{
									if(recordBackInfo.getRoomId().intValue() == roomScore.getRoomId().intValue())
									gamesnum.add(recordBackInfo.getGamesNum());
								}
								
								
								for (Integer gameNum : gamesnum) 
								{
									battleUserList = new ArrayList<Map<String, Object>>();
									gamesNumMap = new HashMap<String, Object>();
									gamesNumMap.put("gamesNum", gameNum);
									for (RecordBackInfo recordBackInfo : battleInfo) 
									{
										if(gameNum.intValue() == recordBackInfo.getGamesNum().intValue()) 
										{
											if(recordBackInfo.getRoomId().intValue() == roomScore.getRoomId().intValue()) 
											{
												if(user != null && user.size() > 0) 
												{
													for (RoomAccountInfo ai : user) 
													{
														if(recordBackInfo.getUserId().longValue() == ai.getUserId().longValue()) 
														{
															battleUser = new HashMap<String, Object>();
															nickName = ai.getNickName();
															if(nickName != null && StringUtils.isNotEmpty(nickName)) 
															{
																battleUser.put("nickName", AESCBCUtils.decrypt(nickName, configResource.getAesUserKey()));
															}
															battleUser.put("gameId", ai.getGameId());
															battleUser.put("score", recordBackInfo.getScore());
															battleUser.put("videoNumber", recordBackInfo.getId());
															battleUserList.add(battleUser);
															break;
														}
													}
												}
												gamesNumMap.put("battleUser", battleUserList);
											}
											
										}
										
										
									}
									gamesNumList.add(gamesNumMap);
								}
								
							}
							scoreMap.put("gamesNum", gamesNumList);
							bList.add(scoreMap);
						}
					}
					
				}
				data.put("bList", bList);
			}
		}
		return data;
	}

	
	/**
	 * 获取亲友圈战绩信息
	 * @param customId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getGourpBattleRecord(Long userId, Integer groupId, Integer apiVersion) throws IllegalAccessException, InvocationTargetException{
		Date endTime = new Date();
		Date startTime = DateUtils.addDay(new Date(), -2);
		List<List<?>> result = hallBattleRecordMapper.callGetClubBattleRecord(groupId, userId, startTime, endTime);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("apiVersion", apiVersion);
		data.put("valid", true);
		data.put("dateTime", System.currentTimeMillis());
		if(result != null && result.size() > 0) {
			List<Map<String, Object>> bList = null;
			
			if(result.get(0) != null && result.get(0).size() > 0) {
				List<RecordBackInfo> battleInfo = (List<RecordBackInfo>)result.get(0);
				List<GameKindItem> game = (List<GameKindItem>)result.get(1);
				List<RoomAccountInfo> user = (List<RoomAccountInfo>) result.get(2);
				List<RoomAccountInfo> host = (List<RoomAccountInfo>) result.get(3);
				List<RoomScore> battle = (List<RoomScore>) result.get(4);
				
				if(battle != null && battle.size() > 0) {
					bList = new ArrayList<Map<String, Object>>();//result list
					String nickName = null;
					Long roomHostId = null;
					Integer kindId = null;
					Integer roomId = null;
					Date dateTime = null;
					List<Map<String, Object>> battleUserList = null;
					Map<String, Object> battleUser = null;
					Map<String, Object> scoreMap = null;
					List<Map<String, Object>> gamesNumList = null;
					Map<String, Object> gamesNumMap = null;
					Set<Integer> gamesnum = new HashSet<Integer>();;
					for (RoomScore roomScore : battle) {
						/** 获取战绩 **/
						scoreMap = new HashMap<String, Object>();
						kindId = roomScore.getKindId();
						roomHostId = roomScore.getRoomHostId();
						roomId = roomScore.getRoomId();
						scoreMap.put("kindId", roomScore.getKindId());
						if(game != null && game.size() > 0) {
							for (GameKindItem gameKindItem : game) {
								if(kindId.intValue() == gameKindItem.getKindId().intValue()) {
									scoreMap.put("kindName", gameKindItem.getKindName());
									break;
								}
							}
						}
						scoreMap.put("roomId", roomId);
						scoreMap.put("baseScore", roomScore.getCellScore());
						
						dateTime = roomScore.getStartTime();
						if(dateTime != null) {
							scoreMap.put("startTime", DateUtils.format(dateTime, "yyyy-MM-dd HH:mm:ss"));
						}
						
						scoreMap.put("roomHostId", roomHostId);
						scoreMap.put("score", roomScore.getScore());
						
						if(roomHostId != null && roomHostId != 0) {
							if(host != null && host.size() > 0) {
								for (RoomAccountInfo ra : host) {
									if(ra.getUserId().longValue() == roomHostId.longValue()) {
										scoreMap.put("gameId", ra.getGameId());
										nickName = ra.getNickName();
										if(StringUtils.isNotEmpty(nickName)) {
											scoreMap.put("nickName", AESCBCUtils.decrypt(nickName, configResource.getAesUserKey()));
										}
									}
									break;
								}
							}
						}
						
						scoreMap.put("playBackCode", roomScore.getPlayBackCode());
						scoreMap.put("guid", roomScore.getPersonalRoomGuid());
						scoreMap.put("chairId", roomScore.getChairId());
						
						
						if(battleInfo != null && battleInfo.size() > 0) {
							gamesNumList = new ArrayList<Map<String, Object>>();
							gamesnum.clear();
							for (RecordBackInfo recordBackInfo : battleInfo) 
							{
								if(recordBackInfo.getRoomId().intValue() == roomScore.getRoomId().intValue())
								gamesnum.add(recordBackInfo.getGamesNum());
							}
							
							
							for (Integer gameNum : gamesnum) 
							{
								battleUserList = new ArrayList<Map<String, Object>>();
								gamesNumMap = new HashMap<String, Object>();
								gamesNumMap.put("gamesNum", gameNum);
								for (RecordBackInfo recordBackInfo : battleInfo) 
								{
									if(gameNum.intValue() == recordBackInfo.getGamesNum().intValue()) 
									{
										if(recordBackInfo.getRoomId().intValue() == roomScore.getRoomId().intValue()) 
										{
											if(user != null && user.size() > 0) 
											{
												for (RoomAccountInfo ai : user) 
												{
													if(recordBackInfo.getUserId().longValue() == ai.getUserId().longValue()) 
													{
														battleUser = new HashMap<String, Object>();
														nickName = ai.getNickName();
														if(nickName != null && StringUtils.isNotEmpty(nickName)) 
														{
															battleUser.put("nickName", AESCBCUtils.decrypt(nickName, configResource.getAesUserKey()));
														}
														battleUser.put("gameId", ai.getGameId());
														battleUser.put("score", recordBackInfo.getScore());
														battleUser.put("videoNumber", recordBackInfo.getId());
														battleUserList.add(battleUser);
														break;
													}
												}
											}
											gamesNumMap.put("battleUser", battleUserList);
										}
										
									}
									
									
								}
								gamesNumList.add(gamesNumMap);
							}
							
						}
						scoreMap.put("gamesNum", gamesNumList);
						bList.add(scoreMap);
					}
				}
				
			}
			data.put("bList", bList);
		}
		
		return data;
	}
	
}

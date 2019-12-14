package com.snake.mcf.sysmgr.service.uphold.impl;

import com.snake.mcf.common.constant.DictionaryConstant;
import com.snake.mcf.common.constant.PayplatformConstant;
import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.ui.paging.query.PagingQuery;
import com.snake.mcf.common.utils.*;
import com.snake.mcf.common.web.result.EasyPageResult;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.mapper.uphold.UpholdGamelsMapper;
import com.snake.mcf.sysmgr.repertory.entity.Mobilekinditem;
import com.snake.mcf.sysmgr.repertory.entity.dto.ComboBoxDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.MobilekinditemDTO;
import com.snake.mcf.sysmgr.repertory.mapper.MobilekinditemMapper;
import com.snake.mcf.sysmgr.service.uphold.UpholdGamelsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName UpholdGamelsServiceImpl
 * @Author 大帅
 * @Date 2019/6/30 15:52
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class UpholdGamelsServiceImpl extends BaseServiceImpl implements UpholdGamelsService {

    @Autowired
    private UpholdGamelsMapper upholdGamelsMapper;

    @Autowired
    private MobilekinditemMapper mobilekinditemMapper;

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;

    @Override
    public PageResult<MobilekinditemDTO> queryGamelsWithPage(EasyPageFilter pageFilter, MobilekinditemDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<MobilekinditemDTO> pageResult = this.queryGamelsWithPage1(pageFilter,dto);
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<MobilekinditemDTO> result = (EasyPageResult<MobilekinditemDTO>) pageResult;
            List<MobilekinditemDTO> rows = result.getRows();

            rows.forEach( (rd) -> {

                Integer clientversion = rd.getClientversion();
                if(clientversion != null){
                    String clientversionDesc = VersionUtils.deserializeVersion(clientversion);
                    rd.setClientversionDesc(clientversionDesc);
                }

                Integer nullity = rd.getNullity();
                if(nullity != null){
                    String nullityDesc = DictionaryConstant.getNullityDesc(nullity);
                    rd.setNullityDesc(nullityDesc);
                }

                //游戏归属
                Integer kindmark = rd.getKindmark();
                if(kindmark != null){
                    String kindmarkDesc = this.getKindmarkDesc(kindmark.intValue());
                    rd.setKindmarkDesc(kindmarkDesc);
                }


            });
        }
        return pageResult;
    }

    private String getKindmarkDesc(int kindmark){
        StringBuilder sb = new StringBuilder();

        int a = (kindmark & 1);
        log.info("a = {}" , a);
        if(a > 0){
            sb.append("ios,");
        }

        int b = (kindmark & 2);
        log.info("b = {}" , b);
        if(b > 0){
            sb.append("android,");
        }
        String str = sb.toString();
        if(StringUtils.isBlank(str)){
            return null;
        }
        str = str.substring(0, str.length() - 1);
        return str;
    }

    private PageResult<MobilekinditemDTO> queryGamelsWithPage1(EasyPageFilter filter, MobilekinditemDTO dto) {
        log.info("服务端:page={}",filter.getPage());
        log.info("服务端:rows={}",filter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(filter);
        //过滤
        Example example = new Example(Mobilekinditem.class);
        Example.Criteria criteria = example.createCriteria();
        if(dto != null){

            String kindname = dto.getKindname();
            if(StringUtils.isNotBlank(kindname)){
                criteria.andLike("kindname", PayplatformConstant.SPLIT_SYMBOL_PER + kindname + PayplatformConstant.SPLIT_SYMBOL_PER);
            }

            Integer nullity = dto.getNullity();
            if(nullity != null){
                criteria.andEqualTo("nullity",nullity);
            }

        }

        //排序
        String orderByClause = easyPageQuery.sortOrderByClause(filter);
        log.info("排序:orderByClause={}",orderByClause);
        if(StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }else{
            example.setOrderByClause(" kindid asc");
        }
        //查询
        List<Mobilekinditem> list = mobilekinditemMapper.selectByExample(example);
        //结果
        PageResult<MobilekinditemDTO> pageResult = easyPageQuery.queryResult(list, MobilekinditemDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }

    @Override
    public List<ComboBoxDTO> loadGameModuleData() {
        List<ComboBoxDTO> list = upholdGamelsMapper.loadGameModuleData();
        ComboBoxDTO comboBoxDTO = new ComboBoxDTO();
        comboBoxDTO.setId("0");
        comboBoxDTO.setText("全部游戏");
        if(CollectionUtils.isNotEmpty(list)){
            list.add(0,comboBoxDTO);
        }
        return list;
    }

    @Override
    public boolean isExistGamels(MobilekinditemDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入对象为空", 1004);
        }
        Integer kindid = dto.getKindid();
        if(kindid == null){
            throw new BusinessException("根据id查询,传入对象 kindid 为空", 1004);
        }
        Mobilekinditem mobilekinditem = mobilekinditemMapper.selectByPrimaryKey(kindid);
        return mobilekinditem != null;
    }

    @Override
    public boolean saveGamels(MobilekinditemDTO dto) {
        String clientversionDesc = dto.getClientversionDesc();
        if(StringUtils.isNotBlank(clientversionDesc)){
            int clientversion = VersionUtils.serializeVersion(clientversionDesc);
            dto.setClientversion(clientversion);
        }
        dto.setKindmark(3);
        dto.setTypeid(0);
        Mobilekinditem record = new Mobilekinditem();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);
        int count = mobilekinditemMapper.insertSelective(record);
        return count > 0;
    }

    @Override
    public MobilekinditemDTO queryGamelsById(MobilekinditemDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入对象为空", 1004);
        }
        Integer kindid = dto.getKindid();
        if(kindid == null){
            throw new BusinessException("根据id查询,传入对象 kindid 为空", 1004);
        }
        Mobilekinditem mobilekinditem = mobilekinditemMapper.selectByPrimaryKey(kindid);
        MobilekinditemDTO record = new MobilekinditemDTO();
        CommonBeans.copyPropertiesIgnoreNull(mobilekinditem,record);
        Integer clientversion = record.getClientversion();
        if(clientversion != null){
            String clientversionDesc = VersionUtils.deserializeVersion(clientversion);
            record.setClientversionDesc(clientversionDesc);
        }

        return record;
    }

    @Override
    public boolean updateGamels(MobilekinditemDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id修改,传入对象为空", 1004);
        }
        Integer kindid = dto.getKindid();
        if(kindid == null){
            throw new BusinessException("根据id修改,传入对象 kindid 为空", 1004);
        }
        String clientversionDesc = dto.getClientversionDesc();
        if(StringUtils.isNotBlank(clientversionDesc)){
            int clientversion = VersionUtils.serializeVersion(clientversionDesc);
            dto.setClientversion(clientversion);
        }
        Mobilekinditem record = new Mobilekinditem();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);
        int count = mobilekinditemMapper.updateByPrimaryKeySelective(record);
        return count > 0;
    }

    @Override
    public boolean batchTransfer(MobilekinditemDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id修改,传入对象为空", 1004);
        }
        String kindids = dto.getKindids();
        if(StringUtils.isBlank(kindids)){
            throw new BusinessException("根据id修改,传入对象 kindids 为空", 1004);
        }

        //List<String> list = new ArrayList<>();
        String[] arr = kindids.split(",");
        /*if(ArrayUtils.isNotEmpty(arr)){
            list = Arrays.asList(arr);
        }

        Mobilekinditem record = new Mobilekinditem();
        record.setNullity(dto.getNullity());

        int count = 0;
        Example example = new Example(Mobilekinditem.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("kindid",list);
        mobilekinditemMapper.updateByExampleSelective(record,example);
        count ++;
        return count > 0;*/

        int count = 0;
        for (String kindid:arr) {
            Mobilekinditem record = new Mobilekinditem();
            record.setKindid(Integer.valueOf(kindid));
            record.setNullity(dto.getNullity());
            mobilekinditemMapper.updateByPrimaryKeySelective(record);
            count++;
        }
        return count > 0;
    }

    @Override
    public boolean removeGamelsByIds(Integer[] kindids) {

        List<Integer> list = new ArrayList<>();
        if(ArrayUtils.isNotEmpty(kindids)){
            list = Arrays.asList(kindids);
        }

        int count = 0;
        Example example = new Example(Mobilekinditem.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("kindid",list);
        mobilekinditemMapper.deleteByExample(example);
        count ++;
        return count > 0;
    }
}

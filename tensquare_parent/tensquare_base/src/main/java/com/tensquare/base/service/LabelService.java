package com.tensquare.base.service;


import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LabelService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LabelService.class);

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    public void save(Label label) {
        label.setId(idWorker.nextId() + "");
        LOGGER.info("label的id:" + label.getId());
        labelDao.save(label);
    }

    public List<Label> findAll() {
        return labelDao.findAll();
    }

    public Label findById(String labelId) {
        return labelDao.findById(labelId).get();
    }

    public void deleteById(String labelId) {
        labelDao.deleteById(labelId);
    }

    public void updateById(String labelId, Label label) {
        label.setId(labelId);
        labelDao.save(label);
    }

    /**
     * 根据条件查询标签集合
     *
     * @param searchMap
     * @return
     */
    public List<Label> findBySearch(Map searchMap) {
        //拼接条件
        return labelDao.findAll(this.getSpecification(searchMap));
    }

    /**
     * 公共的查询条件拼接
     *
     * @param searchMap
     * @return
     */
    public Specification<Label> getSpecification(Map searchMap) {
        return new Specification<Label>() {
            //拼接查询条件
            @Nullable
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // 定一个条件集合
                List<Predicate> predicateList = new ArrayList<>();
                if (!StringUtils.isEmpty(searchMap.get("labelname"))) {
                    //参数1：表达式 labelname=    参数2：参数值searchMap.get("labelname")
                    Predicate p1 = criteriaBuilder.like(root.get("labelname").as(String.class), "%" + searchMap.get("labelname") + "%");
                    predicateList.add(p1);
                }

                if (!StringUtils.isEmpty(searchMap.get("state"))) {
                    //参数1：表达式 labelname=    参数2：参数值searchMap.get("labelname")
                    Predicate p1 = criteriaBuilder.like(root.get("state").as(String.class), "%" + searchMap.get("state") + "%");
                    predicateList.add(p1);
                }

                if (!StringUtils.isEmpty(searchMap.get("count"))) {
                    //参数1：表达式 labelname=    参数2：参数值searchMap.get("labelname")
                    Predicate p1 = criteriaBuilder.like(root.get("count").as(String.class), "%" + searchMap.get("count") + "%");
                    predicateList.add(p1);
                }

                if (!StringUtils.isEmpty(searchMap.get("recommend"))) {
                    //参数1：表达式 labelname=    参数2：参数值searchMap.get("labelname")
                    Predicate p1 = criteriaBuilder.like(root.get("recommend").as(String.class), "%" + searchMap.get("recommend") + "%");
                    predicateList.add(p1);
                }
                if (predicateList == null || predicateList.size() == 0) {
                    return null;
                }
                //list转为 Predicate数组  将集合通过toArray方法转成参数中的数组对象
                /*Predicate[] predicates = new Predicate[predicateList.size()];
                Predicate[] predicates1 = predicateList.toArray(predicates);
                return criteriaBuilder.and(predicates1);*/
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }

    /**
     * 带分页的条件查询
     *
     * @param page
     * @param size
     * @param searchMap
     * @return
     */
    public Page<Label> findByPageSearch(int page, int size, Map searchMap) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return labelDao.findAll(this.getSpecification(searchMap), pageable);
    }

//    @DcmsLogTrace
//    public ApiResponse<Result> findAll2(ApiRequest<Void> req) {
//        ApiResponse<Result> resp = new ApiResponse<>();
//        resp.setResultCode(ApiResponse.SUCCESS);
//        resp.setResultMsg("ok");
//        return resp;
//    }
}
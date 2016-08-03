package com.sera.service.impl;

import com.sera.config.SequenceConfig;
import com.sera.config.TaskConfig;
import com.sera.config.ViewConfig;
import com.sera.dao.FavGroupMapper;
import com.sera.dao.FavListMapper;
import com.sera.entity.FavGroupEntity;
import com.sera.entity.FavListEntity;
import com.sera.entity.WorkTaskEntity;
import com.sera.helper.IconFinder;
import com.sera.helper.SequenceHelper;
import com.sera.helper.UrlHelper;
import com.sera.service.FavService;
import com.sera.task.TaskHelper;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收藏夹操作
 * Created by wangqing on 16/7/12.
 */
@Service
public class FavServiceImpl implements FavService {
    private static final Logger log = LoggerFactory.getLogger(FavServiceImpl.class);

    @Resource
    private FavListMapper favListMapper;
    @Resource
    private FavGroupMapper favGroupMapper;
    @Resource
    private SequenceHelper sequenceHelper;
    @Resource
    private UrlHelper urlHelper;
    @Resource
    private IconFinder iconFinder;
    @Resource
    private TaskHelper taskHelper;
    @Value("#{config['icon.path']}")
    private String iconPath;

    @Override
    public boolean addFavGroup(FavGroupEntity favGroupEntity) {
        if (favGroupEntity.getGroupId() == null || favGroupEntity.getGroupId() <= 0) {
            favGroupEntity.setGroupId(sequenceHelper.getSeq(SequenceConfig.FAV_GROUP_SEQ));
        }
        if (StringUtils.isBlank(favGroupEntity.getGroupName())) {
            favGroupEntity.setGroupName("未命名");
        }
        if (favGroupEntity.getGroupName().length() > 20) {
            favGroupEntity.setGroupName(favGroupEntity.getGroupName().substring(0, 20));
        }
        favGroupEntity.setCovert(1);
        return favGroupMapper.insert(favGroupEntity) > 0;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean delFavGroup(long userId, long groupId) {
        FavGroupEntity favGroupEntity = new FavGroupEntity();
        favGroupEntity.setUserId(userId);
        favGroupEntity.setGroupId(groupId);
        favGroupMapper.delByUserIdAndId(favGroupEntity);
        FavListEntity favListEntity = new FavListEntity();
        favListEntity.setGroupId(groupId);
        favListEntity.setUserId(userId);
        favListMapper.deleteByGroup(favListEntity);
        return true;
    }

    @Override
    public List<FavGroupEntity> findFavGroup(long userId, int cover) {
        FavGroupEntity favGroupEntity = new FavGroupEntity();
        favGroupEntity.setUserId(userId);
        if (cover > 0) {
            favGroupEntity.setCovert(cover);
        }
        return favGroupMapper.find(favGroupEntity);
    }

    @Override
    public List<FavGroupEntity> findForDiscovery(long userId) {
        Map<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        param.put("covert", 1);
        param.put("start", 0);
        param.put("num", 20);
        return favGroupMapper.findForDiscovery(param);
    }

    @Override
    public boolean protectFavGroup(long userId, long groupId, int type) {
        FavGroupEntity favGroupEntity = new FavGroupEntity();
        favGroupEntity.setUserId(userId);
        favGroupEntity.setGroupId(groupId);
        favGroupEntity.setCovert(type);
        return favGroupMapper.updateForProtect(favGroupEntity) > 0;
    }

    @Override
    public boolean addFav(FavListEntity favListEntity) {
        //如果没有分组,自动生成分组
        if (favListEntity.getGroupId() == null || favListEntity.getGroupId() <= 0) {
            FavGroupEntity favGroupEntity = new FavGroupEntity();
            favGroupEntity.setUserId(favListEntity.getUserId());
            favGroupEntity.setGroupName(ViewConfig.KEY_SITE_GROUP_NAME);
            List<FavGroupEntity> list = favGroupMapper.find(favGroupEntity);
            if (null != list && !list.isEmpty()) {
                favListEntity.setGroupId(list.get(0).getGroupId());
            } else {
                long gid = sequenceHelper.getSeq(SequenceConfig.FAV_GROUP_SEQ);
                FavGroupEntity favG = new FavGroupEntity();
                favG.setGroupId(gid);
                favG.setGroupName(ViewConfig.KEY_SITE_GROUP_NAME);
                favG.setUserId(favListEntity.getUserId());
                favG.setUserName(favGroupEntity.getUserName());
                int isIn = favGroupMapper.insert(favG);
                if (isIn <= 0) return false;
                favListEntity.setGroupId(gid);
                favListEntity.setGroupName(ViewConfig.KEY_SITE_GROUP_NAME);
            }
        }

        if (favListEntity.getFavId() == null || favListEntity.getFavId() <= 0) {
            favListEntity.setFavId(sequenceHelper.getSeq(SequenceConfig.FAV_LIST_SEQ));
        }
        favListEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        favListEntity.setFavFocus(1);
        favListEntity.setFavSort(1);
        favListEntity.setFavStatus(1);
        if (StringUtils.isBlank(favListEntity.getGroupName())) {
            favListEntity.setGroupName(favGroupMapper.getByGroupId(favListEntity.getGroupId()).getGroupName());
        }
        if (StringUtils.isBlank(favListEntity.getFavTitle())) {
            favListEntity.setFavTitle(urlHelper.getFormatUrlTitle(favListEntity.getFavUrl()));
        }
//        String icon = iconFinder.getIconUrlString(favListEntity.getFavUrl());
//        if (!StringUtils.isBlank(icon)) {
//            icon = Jsoup.parse(icon).text();
//            icon = icon.replaceAll(" ", "");
//            icon = icon.replaceAll("\"", "");
//            // 输出的文件流
//            String fileName = System.nanoTime() + ".ico";
//            boolean isDown = downloadIcon(icon, fileName);
//            if (isDown) {
//                favListEntity.setFavIco(fileName);
//            }
//        }
        return favListMapper.insert(favListEntity) > 0;
    }

    @Override
    public boolean delFav(long userId, long favId) {
        FavListEntity favListEntity = new FavListEntity();
        favListEntity.setUserId(userId);
        favListEntity.setFavId(favId);
        return favListMapper.delByUserIdAndId(favListEntity) > 0;
    }

    @Override
    public boolean addCheckFavTask(long userId) {
        WorkTaskEntity taskCheck = taskHelper.getTaskByRefId(String.valueOf(userId) + "_" + TaskConfig.CHECK_URL_TASK_TYPE);
        if (null == taskCheck || taskCheck.getStatus() != 1) {
            taskHelper.createTask(String.valueOf(userId) + "_" + TaskConfig.CHECK_URL_TASK_TYPE, TaskConfig.CHECK_URL_TASK_TYPE, String.valueOf(userId));
        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean checkFav(long userId) {
        Map<String, Object> param = new HashMap<>();
        param.put("userId", userId);

        long total = favListMapper.findTotal(param);
        if (total <= 0) return true;
        int limit = 200;
        for (int i = 0; i <= total; i = i + limit) {
            param.put("start", i);
            param.put("num", limit);
            List<FavListEntity> list = favListMapper.findSimple(param);
            for (FavListEntity entity : list) {
                boolean isOK = urlHelper.isValid(entity.getFavUrl());
                if ((entity.getFavStatus() == null || entity.getFavStatus() == 1) && !isOK) {
                    entity.setFavStatus(2);
                    entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                    log.info("URL检查发现无效:" + entity.getFavUrl());
                    favListMapper.updateStatus(entity);
                } else if (entity.getFavStatus() == 2 && isOK) {
                    entity.setFavStatus(1);
                    entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                    favListMapper.updateStatus(entity);
                }
            }
        }
        return true;
    }

    @Override
    public FavListEntity isDuplicate(long userId, String url) {
        FavListEntity entity = new FavListEntity();
        entity.setUserId(userId);
        entity.setFavUrl(url);
        return favListMapper.getByUrl(entity);
    }

    @Override
    public boolean arrange(long userId) {
        return false;
    }

    @Override
    public boolean delExpireFav(long userId) {
        FavListEntity favListEntity = new FavListEntity();
        favListEntity.setUserId(userId);
        favListEntity.setFavStatus(2);
        favListMapper.deleteByStatus(favListEntity);
        return true;
    }

    @Override
    public int focusFav(long userId, long favId) {
        FavListEntity favListEntity = new FavListEntity();
        favListEntity.setUserId(userId);
        favListEntity.setFavId(favId);
        FavListEntity fav = favListMapper.get(favListEntity);
        if (fav == null) return 0;

        int favFocus = fav.getFavFocus();
        if (1 == favFocus) {
            favFocus = 2;
        } else {
            favFocus = 1;
        }
        favListEntity.setFavFocus(favFocus);
        favListEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        int num = favListMapper.updateFocus(favListEntity);
        if (num > 0) {//修改排序
            if (favFocus == 2) {
                favListMapper.updateHighSort(favListEntity);
            } else {
                favListMapper.updateLowSort(favListEntity);
            }
        }
        return favFocus;
    }

    @Override
    public List<FavListEntity> findByGroup(long userId, long groupId, String searchKey, int start, int num) {
        Map<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        param.put("groupId", groupId);
        param.put("searchKey", searchKey);
        param.put("start", start);
        param.put("num", num);
        return favListMapper.find(param);
    }

    @Override
    public boolean updateFavGroup(long userId, long favId, long groupId) {
        FavListEntity favListEntity = new FavListEntity();
        favListEntity.setUserId(userId);
        favListEntity.setGroupId(groupId);
        favListEntity.setFavId(favId);
        favListEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        favListMapper.updateFavGroup(favListEntity);
        return true;
    }

    @Override
    public boolean updateTitle(long userId, long favId, String title) {
        FavListEntity favListEntity = new FavListEntity();
        favListEntity.setUserId(userId);
        favListEntity.setFavId(favId);
        favListEntity.setFavTitle(title);
        favListEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        favListMapper.updateTitle(favListEntity);
        return true;
    }

    private boolean downloadIcon(String urlStr, String fileName) {
        try {
            String path = iconPath;
            if (StringUtils.isBlank(path)) {
                return false;
            }
            File ff = new File(path);
            if (!ff.exists()) {
                boolean ismk = ff.mkdir();
                if (!ismk) return false;
            }
            // 构造URL
            URL url = new URL(urlStr);
            // 打开连接
            URLConnection con = url.openConnection();
            //：设置连接主机超时（单位：毫秒）
            con.setConnectTimeout(200);
            //设置从主机读取数据超时（单位：毫秒）
            con.setReadTimeout(500);
            // 输入流
            InputStream is = con.getInputStream();
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;

            OutputStream os = new FileOutputStream(ff + File.separator + fileName);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            // 完毕，关闭所有链接
            os.close();
            is.close();
            return true;
        } catch (Exception e) {
            log.error("下载icon失败:", e.getMessage());
            return false;
        }
    }
}

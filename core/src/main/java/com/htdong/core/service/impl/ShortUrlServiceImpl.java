package com.htdong.core.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.htdong.client.domain.db.ShortUrlDO;
import com.htdong.core.service.ShortUrlService;
import com.htdong.dal.mapper.ShortUrlMapper;

@Service("shortUrlService")
public class ShortUrlServiceImpl implements ShortUrlService {

    private static final char[] BASE62 = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
            'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
            'v', 'w', 'x', 'y', 'z' };

    @Resource
    private ShortUrlMapper shortUrlMapper;

    @Override
    public String getUrl(String shortPath) {
        QueryWrapper<ShortUrlDO> query = new QueryWrapper<>();
        query.eq(ShortUrlDO.DB_FIELD_SHORT_PATH, shortPath);
        ShortUrlDO domain = shortUrlMapper.selectOne(query);
        return domain == null ? null : domain.getShortPath();
    }

    @Override
    public void addShortUrl(String url) {
        ShortUrlDO domain = new ShortUrlDO();
        domain.setRealUrl(url);
        domain.setShortPath(currentTimeBase62());
        shortUrlMapper.insert(domain);
    }

    private static String currentTimeBase62() {
        long now = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        while (now > 0) {
            sb.append(BASE62[(int) (now % 62)]);
            now /= 62;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(currentTimeBase62());
    }
}
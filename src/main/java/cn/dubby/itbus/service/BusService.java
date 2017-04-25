package cn.dubby.itbus.service;

import cn.dubby.itbus.bean.Bus;
import cn.dubby.itbus.constant.EmailTemplate;
import cn.dubby.itbus.dao.BusDao;
import cn.dubby.itbus.service.dto.ModifyResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;

/**
 * Created by yangzheng03 on 2017/4/15.
 */
@Service
public class BusService {

    private static final Logger logger = LoggerFactory.getLogger(BusService.class);

    @Autowired
    private BusDao busDao;

    @Autowired
    private EmailService emailService;

    private static final int MAX_NUM = 100;
    private static final int MIN_NUM = 0;

    private static final int PAGE_SIZE = 20;

    public List<Bus> selectTopN(int limit) {
        if (limit < MIN_NUM || limit > MAX_NUM) {
            limit = MAX_NUM;
        }
        return busDao.selectTopN(limit);
    }

    public List<Bus> listByLine(int lineId, int pageId) {
        int offset = (pageId - 1) * PAGE_SIZE;
        return busDao.selectByLine(lineId, offset, PAGE_SIZE);
    }

    public Bus detail(int busId) {
        Bus bus = busDao.selectByPrimaryKey(busId);
        if (bus == null) {
            logger.error("detail not exist,id:" + busId);
            return null;
        }

        bus.setBusTicket(null);
        return bus;
    }

    public ModifyResult<Bus> save(int busLineId, String busName, String busContent, String email) {
        if (busLineId < 0 || StringUtils.isEmpty(busContent) || StringUtils.isEmpty(busName)) {
            return ModifyResult.PARAMS_ERROR;
        }

        Bus bus = new Bus();
        bus.setBusContent(busContent);
        bus.setBusName(busName);
        bus.setBusLineId(busLineId);
        bus.setBusTicket(UUID.randomUUID().toString() + "-" + UUID.randomUUID().toString());

        try {
            busDao.insertSelective(bus);
            bus = busDao.selectByPrimaryKey(bus.getId());

            if (!StringUtils.isEmpty(email)) {
                sendThanksEmail(email, bus.getId(), bus.getBusTicket());
            }

        } catch (Exception e) {
            logger.error("save", e);
            return ModifyResult.SYSTEM_EXCEPTION;
        }

        return new ModifyResult<>(bus);
    }

    private void sendThanksEmail(String email, int busId, String ticket) {
        try {
            emailService.sendEmail(email, EmailTemplate.THANKS_WRITE_EMAIL_SUBJECT, String.format(EmailTemplate.THANKS_WRITE_EMAIL_CONTENT, busId, ticket));
        } catch (Exception e) {
            logger.error("sendThanksEmail error", e);
        }
    }

    public ModifyResult<Bus> update(int busId, String ticket, int busLineId, String busName, String busContent) {
        if (busId < 0 || StringUtils.isEmpty(ticket) || busLineId < 0 || StringUtils.isEmpty(busContent) || StringUtils.isEmpty(busName)) {
            return ModifyResult.PARAMS_ERROR;
        }

        Bus bus = busDao.selectByPrimaryKey(busId);
        if (bus == null) {
            logger.error("update not exist,id:" + busId);
            return save(busLineId, busName, busContent, null);
        }

        if (!ticket.equals(bus.getBusTicket())) {
            logger.error("ticket error,id:" + busId + ",ticket:" + ticket);
            return ModifyResult.TICKET_ERROR;
        }

        bus.setCreateTime(null);
        bus.setUpdateTime(null);
        bus.setBusContent(busContent);
        bus.setBusName(busName);
        bus.setBusLineId(busLineId);

        try {
            busDao.updateByPrimaryKeySelective(bus);

            bus = busDao.selectByPrimaryKey(busId);
        } catch (Exception e) {
            logger.error("save", e);
            return ModifyResult.SYSTEM_EXCEPTION;
        }

        return new ModifyResult<>(bus);
    }

}

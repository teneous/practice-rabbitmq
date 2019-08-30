package com.trifail.practice.api;

/**
 * api中所使用到的队列用例
 *
 * @author syoka
 */
public class Constant {

    public static final String ELE_DIRECT_EXCHANGE = "ele.direct.exchange";
    public static final String ELE_TOPIC_EXCHANGE = "ele.topic.exchange";
    public static final String ELE_FANOUT_EXCHANGE = "ele.fanout.exchange";

    public static final String ELE_DIRECT_QUEUE = "ele.direct.queue";
    public static final String ELE_TOPIC_QUEUE = "ele.topic.queue";
    public static final String ELE_FANOUT_QUEUE = "ele.fanout.queue";

    public static final String ELE_DIRECT_ROUTINGKEY = "ele.direct";
    public static final String ELE_TOPIC_ROUTINGKEY = "ele.topic.#";


    public static final String DLX_EXCHANGE_ARGS = "x-dead-letter-exchange";
    public static final String DLX_ROUTING_ARGS = "x-dead-letter-routing-key";
    public static final String TTL_ARGS = "x-message-ttl";

    public static final String TOPIC = "topic";
    public static final String DIRECT = "direct";
    public static final String FANOUT = "fanout";
    public static final String HEADER = "header";


    public static final String ADV_ACK_QUEUE = "adv.ack.queue";
    public static final String ADV_ACK_EXCHANGE = "adv.ack.exchange";
    public static final String ADV_ACK_ROUTING_KEY = "adv.ack.#";

    public static final String ADV_DLX_QUEUE = "adv.dlx.queue";
    public static final String ADV_DLX_EXCHANGE = "adv.dlx.exchange";

    public static final String ADV_DLX_NORMAL_EXCHANGE = "adv.dlx.normal.exchange";
    public static final String ADV_DLX_NORMAL_QUEUE = "adv.dlx.normal.queue";
    public static final String ADV_DLX_NORMAL_ROUTING_KEY = "adv.dlx.#";

    public static final String ADV_CONFIRM_EXCHANGE = "adv.confirm.exchange";
    public static final String ADV_CONFIRM_QUEUE = "adv.confirm.queue";
    public static final String ADV_CONFIRM_ROUTING_KEY = "adv.confirm.#";

    public static final String ADV_RETURN_EXCHANGE = "adv.return.exchange";
    public static final String ADV_RETURN_QUEUE = "adv.return.queue";
    public static final String ADV_RETURN_ROUTING_KEY = "adv.return.#";
    public static final String ADV_RETURN_ERROR_ROUTING_KEY = "error.return.test";

    public static final String ADV_QOS_EXCHANGE = "adv.qos.exchange";
    public static final String ADV_QOS_QUEUE = "adv.qos.queue";
    public static final String ADV_QOS_ROUTING_KEY = "adv.qos.#";
}

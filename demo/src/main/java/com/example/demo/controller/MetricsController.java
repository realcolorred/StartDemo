package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by new on 2020/6/9.
 */
@RestController
@RequestMapping("/metrics")
public class MetricsController {

    @RequestMapping(value = { "/query", "/", "" })
    public ResponseEntity<String> query() {
        String msg =
            "node_openstack_bandwidth_order{vpcNamespace=\"vpc-g0od\",dstIp=\"114.114.114.114\",lossRate=\"100\",hasOrderBandwidth=\"true\", tenantId=\"1\",regionId=\"1\",bandName=\"金碧物业B49\"} 0\n"
                + "eip_num{cidr=\"100.97.0.0/16\",ip_version=\"4\",subnet_id=\"2fa32014-bca5-4641-8a9b-ce6beb144e69\",subnet_name=\"ext-internal2\",total_ips=\"65533\", used_ips=\"1831\"} 63702\n"
                + "eip_num{cidr=\"100.96.0.0/16\",ip_version=\"4\",subnet_id=\"43f1158a-ffe0-49b2-a534-9ebeba86c2a0\",subnet_name=\"ext-internal1\",total_ips=\"65533\", used_ips=\"4\"} 65529\n"
                + "eip_num{cidr=\"100.64.0.0/22\",ip_version=\"4\",subnet_id=\"35bf8e0d-daee-4e2c-8014-6cc75a6aefc1\",subnet_name=\"ext-internal\",total_ips=\"1017\", used_ips=\"70\"} 947\n"
                + "eip_num{cidr=\"175.6.218.24/29\",ip_version=\"4\",subnet_id=\"39bd89de-00c5-4fd9-b0c7-edb6a9b37ff1\",subnet_name=\"ext-subnet1\",total_ips=\"5\", used_ips=\"5\"} 0\n"
                + "eip_num{cidr=\"175.6.220.0/24\",ip_version=\"4\",subnet_id=\"3b3d99ce-6904-4bad-9021-7c2d0aa95836\",subnet_name=\"ext-subnet2\",total_ips=\"253\", used_ips=\"243\"} 10\n"
                + "eip_num{cidr=\"175.6.219.0/24\",ip_version=\"4\",subnet_id=\"62e02975-c832-4443-8c62-96f8246c10a2\",subnet_name=\"ext-subnet3\",total_ips=\"253\", used_ips=\"0\"} 253\n"
                + "eip_num{cidr=\"10.43.154.0/24\",ip_version=\"4\",subnet_id=\"55f0c8e8-42d8-41a0-b1c4-77d9441ebc28\",subnet_name=\"ext-toc-sub1\",total_ips=\"253\", used_ips=\"3\"} 250";

        return ResponseEntity.ok().header("Content-Type", "text/plain").body(msg);
    }
}

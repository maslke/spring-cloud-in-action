package com.maslke.spring.demos.licensingservice.repository;

import com.maslke.spring.demos.licensingservice.model.Organization;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrganizationRedisRepositoryImpl implements OrganizationRedisRepository {

    private RedisTemplate<String, Object> redisTemplate;

    private static final String HASH_NAME = "organization";

    public OrganizationRedisRepositoryImpl(RedisTemplate<String, Object> template) {
        this.redisTemplate = template;
    }

    @Override
    public void saveOrganization(Organization organization) {
        redisTemplate.opsForHash().put(HASH_NAME, organization.getOrganizationId(), organization);
    }

    @Override
    public void updateOrganization(Organization organization) {
        redisTemplate.opsForHash().put(HASH_NAME, organization.getOrganizationId(), organization);
    }

    @Override
    public void deleteOrganization(Organization organization) {
        redisTemplate.opsForHash().delete(HASH_NAME, organization.getOrganizationId(), organization);
    }

    @Override
    public Organization findOrganization(String organizationId) {
        return (Organization) redisTemplate.opsForHash().get(HASH_NAME, organizationId);
    }
}

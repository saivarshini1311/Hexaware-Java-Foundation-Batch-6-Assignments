package dao;

import entity.Policy;
import util.DBConnUtil;
import util.DBPropertyUtil;
import exception.PolicyNotFoundException;
import exception.InvalidPolicyException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class InsuranceServiceImpl implements IPolicyService {
    private Connection connection;

    
    public InsuranceServiceImpl() {
        String connectionString = DBPropertyUtil.getPropertyString("db.properties");
        this.connection = DBConnUtil.getConnection(connectionString);
    }

    @Override
    public boolean createPolicy(Policy policy) throws InvalidPolicyException {
        if (policy == null || policy.getPolicyName() == null || policy.getPolicyName().isEmpty()) {
            throw new InvalidPolicyException("Policy is invalid or missing mandatory details.");
        }

        String query = "INSERT INTO Policy (policyId, policyName, coverageAmount, premium) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, policy.getPolicyId());
            statement.setString(2, policy.getPolicyName());
            statement.setDouble(3, policy.getCoverageAmount());
            statement.setDouble(4, policy.getPremium());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Policy getPolicy(int policyId) throws PolicyNotFoundException {
        String query = "SELECT * FROM Policy WHERE policyId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, policyId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Policy(
                    resultSet.getInt("policyId"),
                    resultSet.getString("policyName"),
                    resultSet.getDouble("coverageAmount"),
                    resultSet.getDouble("premium")
                );
            } else {
                throw new PolicyNotFoundException("Policy with ID " + policyId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<Policy> getAllPolicies() {
        Collection<Policy> policies = new ArrayList<>();
        String query = "SELECT * FROM Policy";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                policies.add(new Policy(
                    resultSet.getInt("policyId"),
                    resultSet.getString("policyName"),
                    resultSet.getDouble("coverageAmount"),
                    resultSet.getDouble("premium")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return policies;
    }

    @Override
    public boolean updatePolicy(Policy policy) throws PolicyNotFoundException, InvalidPolicyException {
        if (policy == null || policy.getPolicyName() == null || policy.getPolicyName().isEmpty()) {
            throw new InvalidPolicyException("Invalid policy details provided.");
        }

        String query = "UPDATE Policy SET policyName = ?, coverageAmount = ?, premium = ? WHERE policyId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, policy.getPolicyName());
            statement.setDouble(2, policy.getCoverageAmount());
            statement.setDouble(3, policy.getPremium());
            statement.setInt(4, policy.getPolicyId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new PolicyNotFoundException("Policy with ID " + policy.getPolicyId() + " not found for update.");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deletePolicy(int policyId) throws PolicyNotFoundException {
        String query = "DELETE FROM Policy WHERE policyId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, policyId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted == 0) {
                throw new PolicyNotFoundException("Policy with ID " + policyId + " not found for deletion.");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

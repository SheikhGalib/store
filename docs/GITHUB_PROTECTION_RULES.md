# 🔒 GitHub Branch Protection Rules Guide

> ✅ **Protection Status:** Active on `main` branch  
> 📅 **Last Verified:** February 17, 2026

This guide explains how to set up branch protection rules to protect your production branches and enforce code quality.

## 📋 Table of Contents
1. [Why Branch Protection?](#why-branch-protection)
2. [Setting Up Branch Protection Rules](#setting-up-branch-protection-rules)
3. [Recommended Rules for Production](#recommended-rules-for-production)
4. [CI/CD Workflow Explanation](#cicd-workflow-explanation)
5. [Secrets Configuration](#secrets-configuration)

---

## 🤔 Why Branch Protection?

Branch protection rules help you:
- ✅ Prevent direct pushes to production branches
- ✅ Require code reviews before merging
- ✅ Ensure all tests pass before merging
- ✅ Maintain code quality and security
- ✅ Create an audit trail of changes

---

## 🛠️ Setting Up Branch Protection Rules

### Step 1: Navigate to Repository Settings

1. Go to your GitHub repository
2. Click **Settings** (gear icon)
3. In the left sidebar, click **Branches** under "Code and automation"

### Step 2: Add Branch Protection Rule

1. Click **Add branch protection rule**
2. In "Branch name pattern", enter: `main` (or `master`)

### Step 3: Configure Protection Options

Enable the following options:

---

## ⚙️ Recommended Rules for Production

### For `main` or `master` branch:

#### ✅ **Require a pull request before merging**
```
☑ Require a pull request before merging
  ☑ Require approvals: 1 (or more for larger teams)
  ☑ Dismiss stale pull request approvals when new commits are pushed
  ☑ Require review from Code Owners (optional)
  ☑ Require approval of the most recent reviewable push
```

#### ✅ **Require status checks to pass before merging**
```
☑ Require status checks to pass before merging
  ☑ Require branches to be up to date before merging
  
  Status checks that are required:
  - Build and Test (from ci.yml)
  - Code Quality Check (from ci.yml)
```

#### ✅ **Require conversation resolution before merging**
```
☑ Require conversation resolution before merging
```

#### ✅ **Require signed commits** (Optional but recommended)
```
☑ Require signed commits
```

#### ✅ **Require linear history** (Optional)
```
☑ Require linear history
```

#### ✅ **Do not allow bypassing the above settings**
```
☑ Do not allow bypassing the above settings
```

#### ✅ **Restrict who can push to matching branches**
```
☑ Restrict who can push to matching branches
  - Add specific users or teams who can push
```

### For `develop` branch (if using GitFlow):

```
☑ Require a pull request before merging
  ☑ Require approvals: 1
☑ Require status checks to pass before merging
  - Build and Test
```

---

## 🔄 CI/CD Workflow Explanation

### Workflow File: `.github/workflows/ci.yml`

```
┌─────────────────────────────────────────────────────────────┐
│                    CI/CD Pipeline                            │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  ┌─────────┐                                                │
│  │  Push   │ ──► Triggers on push to main/master/develop    │
│  │  or PR  │                                                │
│  └────┬────┘                                                │
│       │                                                      │
│       ▼                                                      │
│  ┌─────────────────┐                                        │
│  │  Build & Test   │ ──► Compile code, run 99 tests        │
│  │     (Job 1)     │                                        │
│  └────────┬────────┘                                        │
│           │                                                  │
│     ┌─────┴─────┐                                           │
│     ▼           ▼                                           │
│  ┌──────────┐ ┌──────────────┐                              │
│  │  Code    │ │  Security    │                              │
│  │ Quality  │ │    Scan      │                              │
│  │ (Job 2)  │ │   (Job 3)    │                              │
│  └────┬─────┘ └──────────────┘                              │
│       │                                                      │
│       ▼                                                      │
│  ┌─────────────────┐                                        │
│  │    Package      │ ──► Only on main/master branch         │
│  │     (Job 4)     │                                        │
│  └────────┬────────┘                                        │
│           │                                                  │
│           ▼                                                  │
│  ┌─────────────────┐                                        │
│  │  Docker Build   │ ──► Build container image              │
│  │     (Job 5)     │                                        │
│  └─────────────────┘                                        │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

### Jobs Explained:

| Job | Purpose | Runs On |
|-----|---------|---------|
| **Build and Test** | Compiles code, runs all 99 unit & integration tests | Every push/PR |
| **Code Quality** | Runs Maven verify for code quality checks | After build passes |
| **Security Scan** | OWASP dependency vulnerability check | After build passes |
| **Package** | Creates JAR artifact | Only on main/master push |
| **Docker Build** | Builds Docker image | Only on main/master push |

---

## 🔐 Secrets Configuration

### Required Secrets (for Docker deployment):

1. Go to **Settings** → **Secrets and variables** → **Actions**
2. Click **New repository secret**
3. Add the following secrets:

| Secret Name | Description |
|-------------|-------------|
| `DOCKER_USERNAME` | Your Docker Hub username |
| `DOCKER_PASSWORD` | Your Docker Hub access token |

### How to Create Docker Hub Access Token:

1. Go to [Docker Hub](https://hub.docker.com/)
2. Click your profile → **Account Settings**
3. Go to **Security** → **New Access Token**
4. Name it (e.g., "GitHub Actions")
5. Copy the token and add it as `DOCKER_PASSWORD` secret

---

## 📊 Status Checks Configuration

After your first CI run, configure required status checks:

1. Go to **Settings** → **Branches** → Edit your protection rule
2. Under "Require status checks to pass":
   - Search for and select: **Build and Test**
   - Search for and select: **Code Quality Check**

---

## 🏷️ Recommended Branch Strategy

```
main (production)
  │
  ├── Protected: Requires PR + Reviews + CI pass
  │
  └── develop (integration)
        │
        ├── Protected: Requires PR + CI pass
        │
        ├── feature/add-enrollment
        ├── feature/add-grading
        ├── bugfix/fix-login
        └── hotfix/security-patch
```

### Workflow:
1. Create feature branch from `develop`
2. Make changes and push
3. Create PR to `develop`
4. CI runs automatically
5. Get code review approval
6. Merge to `develop`
7. Create PR from `develop` to `main` for release
8. CI runs + additional reviews
9. Merge to `main` (production)

---

## 📝 Quick Setup Checklist

### Repository Settings:
- [ ] Enable branch protection for `main`
- [ ] Require pull request before merging
- [ ] Require at least 1 approval
- [ ] Require status checks to pass
- [ ] Add required status checks (Build and Test, Code Quality Check)
- [ ] Enable "Require branches to be up to date"

### Secrets:
- [ ] Add `DOCKER_USERNAME` (if using Docker)
- [ ] Add `DOCKER_PASSWORD` (if using Docker)

### First Run:
- [ ] Push code to trigger CI
- [ ] Verify all jobs pass
- [ ] Configure status checks after first successful run

---

## 🚨 Common Issues & Solutions

### Issue: Status checks not appearing
**Solution:** Push code first to trigger CI, then configure status checks.

### Issue: CI failing on tests
**Solution:** Run `./mvnw test` locally to debug test failures.

### Issue: Docker build failing
**Solution:** Ensure Dockerfile exists and secrets are configured.

---

## 📚 Additional Resources

- [GitHub Branch Protection Documentation](https://docs.github.com/en/repositories/configuring-branches-and-merges-in-your-repository/managing-protected-branches)
- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Maven CI/CD Best Practices](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html)

---

**Created for:** Student Management System  
**Last Updated:** February 17, 2026

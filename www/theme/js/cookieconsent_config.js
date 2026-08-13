// Function to update Google Consent state dynamically
function updateGoogleConsent(categories) {
  const analyticsGranted = categories.includes('analytics');

  gtag('consent', 'update', {
    'analytics_storage': analyticsGranted ? 'granted' : 'denied'
  });
}

const __banner_description = `We use cookies on our site to enhance your
experience and analyze traffic. Read our
<a href="https://docs.cloudpave.com/privacy_policy">privacy policy</a> to
learn more.`;

// Run CookieConsent
CookieConsent.run({
  // Automatically clear Google Analytics cookies if consent is revoked
  categories: {
    necessary: {
      enabled: true,
      readOnly: true // Users cannot disable necessary cookies
    },
    analytics: {
      autoClear: {
        cookies: [
          { name: /^_ga/ }, // Matches _ga, _ga_XXXXXX, etc.
          { name: '_gid' }
        ]
      }
    }
  },

  // Trigger consent updates to Google Analytics when preferences change
  onConsent: ({ cookie }) => {
    updateGoogleConsent(cookie.categories);
  },
  onChange: ({ cookie }) => {
    updateGoogleConsent(cookie.categories);
  },

  // Language & UI Configuration
  language: {
    default: 'en',
    translations: {
      en: {
        consentModal: {
          title: 'We use cookies',
          description: __banner_description,
          acceptAllBtn: 'Accept all',
          acceptNecessaryBtn: 'Reject non-essential',
          showPreferencesBtn: 'Preferences'
        },
        preferencesModal: {
          title: 'Cookie Preferences',
          acceptAllBtn: 'Accept all',
          acceptNecessaryBtn: 'Reject non-essential',
          savePreferencesBtn: 'Save preferences',
          closeIconLabel: 'Close modal',
          sections: [
            {
              description: __banner_description
            },
            {
              title: 'Strictly Necessary Cookies',
              description: 'These cookies are essential for the core functionality of the website and cannot be switched off. They are usually set in response to actions made by you, such as logging in or filling in forms.',
              linkedCategory: 'necessary'
            },
            {
              title: 'Performance & Analytics',
              description: 'These cookies allow us to count visits and traffic sources so we can measure and improve performance using Google Analytics. All information collected is aggregated.',
              linkedCategory: 'analytics'
            },
            {
              title: 'California Residents: Do Not Sell or Share My Personal Information',
              description: 'Under the California Consumer Privacy Act (CCPA/CPRA), you have the right to opt out of the "sale" or "sharing" of your personal information for cross-context behavioral advertising. Toggling off Performance & Analytics category above exercises this opt-out right.'
            }
          ]
        }
      }
    }
  }
});
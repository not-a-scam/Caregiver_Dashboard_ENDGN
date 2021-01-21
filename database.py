from firebase import firebase
import re
import json
from dateutil.parser import parse

# Get firebase database
fire_base = firebase.FirebaseApplication('https://fyptester-34d1d.firebaseio.com', None)
newfirebase = firebase.FirebaseApplication('https://rnd-2020-76d58-default-rtdb.firebaseio.com/', None)

# Get data from each branch in firebase
users = fire_base.get('/Users', None)
activities_old = fire_base.get('/Activity Tracker', None)
hr = fire_base.get('/MaxHeartRate', None)
steps = fire_base.get('/Steps Count', None)
moderatemin = fire_base.get('/Weekly Moderate Mins', None)

activities = {}


# format activities for consistency in database branches
def format_activities():
    global activities
    activity_dict = {}
    for user in activities_old:
        activity_dict[user] = {}
        for date in activities_old[user]:
            activity_dict[user][date] = {}
            for activity in activities_old[user][date]:
                if "avrHeartRate" not in activities_old[user][date][activity].keys():
                    activities_old[user][date][activity]["avrHeartRate"] = "No Data"
                if "cDuration" in activities_old[user][date][activity].keys():
                    del activities_old[user][date][activity]["cDuration"]
                activity_dict[user][date][activity] = activities_old[user][date][activity];

    activities = activity_dict


# Organise data by users
def sort_by_user():
    user_dict = {}
    for user in users:
        user_dict[user] = {}
        user_dict[user]['userInfo'] = users[user]
    for user_a in activities:
        for user in user_dict:
            if user_a == user:
                user_dict[user]['activityTrack'] = activities[user_a]
    for user_a in hr:
        for user in user_dict:
            if user_a == user:
                user_dict[user]['heartRate'] = hr[user_a]
    for user_a in steps:
        for user in user_dict:
            if user_a == user:
                user_dict[user]['steps'] = steps[user_a]
    for user_a in moderatemin:
        for user in user_dict:
            if user_a == user:
                user_dict[user]['moderateMin'] = moderatemin[user_a]

    return user_dict


# Organise user data by date
def sort_by_date(byuser):
    user_dict = {}
    for user in users:
        user_dict[user] = {}
        user_dict[user]['userInfo'] = users[user]
    for user in byuser:
        user_dict[user]['StatsByDate'] = {}
        move_data_for_date('activityTrack', user, byuser, user_dict)
        move_data_for_date('heartRate', user, byuser, user_dict, is_hr=True)
        move_data_for_date('steps', user, byuser, user_dict, is_step=True)
        if 'moderateMin' in byuser[user].keys():
            user_dict[user]['moderateMin'] = byuser[user]['moderateMin']

    return user_dict


# Move data from each date from old dictionary to new dictionary
def move_data_for_date(key, user, old_dict, new_dict, is_step=False, is_hr=False):
    if key in old_dict[user].keys():
        for date in old_dict[user][key]:
            dateobj = formatdate(date)
            if dateobj.strftime('%m-%d-%Y') not in new_dict[user]['StatsByDate'].keys():
                new_dict[user]['StatsByDate'][dateobj.strftime('%m-%d-%Y')] = {}
            if is_step:
                new_dict[user]['StatsByDate'][dateobj.strftime('%m-%d-%Y')][key] = old_dict[user][key][date][key]
            elif is_hr:
                new_dict[user]['StatsByDate'][dateobj.strftime('%m-%d-%Y')][key] = old_dict[user][key][date]["hr"]
            else:
                new_dict[user]['StatsByDate'][dateobj.strftime('%m-%d-%Y')][key] = old_dict[user][key][date]


# Format date to a single format
def formatdate(date_str):
    re1 = re.search('(\d\d\d\d)(\d\d)(\d\d)', date_str)  # yyyy mm dd
    re2 = re.search('(\d\d)(\d\d)(\d\d\d\d)', date_str)  # dd mm yyyy
    if (int(re1.group(1)) >= 2020) and (1 <= int(re1.group(2)) <= 12):
        format_date = '.'.join(re1.groups())
        date_obj = parse(format_date, dayfirst=False, yearfirst=True)
    else:
        format_date = '.'.join(re2.groups())
        date_obj = parse(format_date, dayfirst=True, yearfirst=False)

    return date_obj


def get_data():
    format_activities()
    fdata = sort_by_date(sort_by_user())
    fversions = newfirebase.get('/', None)
    if fversions != None:
        for fversion in fversions:
            newfirebase.delete('/', fversion)
    newfirebase.post('/', fdata)


def main():
    get_data()
    versions = newfirebase.get('/', None)
    for version in versions:
        data = newfirebase.get('/'+version, None)

    with open('userData.JSON', 'w') as outfile:
        json.dump(data, outfile)


if __name__ == "__main__":
    get_data()

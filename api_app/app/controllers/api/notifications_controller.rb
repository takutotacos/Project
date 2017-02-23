require 'pry'
module Api
  class NotificationsController < ApplicationController

    def index
      @notifications = current_user.notifications
      render 'notifications', formats: 'json', handlers: 'jbuilder'
    end

    def show
      @notification = Notification.find(params[:id])
      @notification.update read: true
      # @notification.posting
      render 'notification', formats: 'json', handlers: 'jbuilder'
    end

  end
end
